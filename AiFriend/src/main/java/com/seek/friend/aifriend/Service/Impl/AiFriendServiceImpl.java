package com.seek.friend.aifriend.Service.Impl;

import com.seek.friend.aifriend.AiFriendSystemMessage.CompleteFactory;
import com.seek.friend.aifriend.Caffeine.AiFriendCaffeine;
import com.seek.friend.aifriend.Mapper.AiFriendMapper;
import com.seek.friend.aifriend.Service.AiFriendService;
import com.seek.friend.config.NacosConfig.AiFriend.AiFriendParamsRulesConfig;
import com.seek.friend.config.NacosConfig.AiFriend.AiFriendRedisKeyConfig;
import com.seek.friend.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.friend.config.NacosConfig.RocketMQBindConfig.AiFriendTopic;
import com.seek.friend.configobject.RedisData.RedisKeyData;
import com.seek.friend.mqutil.RocketMQ.RocketMQUtil;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;
import com.seek.friend.serviceobject.Common.ChangeAmountDTO;
import com.seek.friend.util.CommonUtil.IdUtil;
import com.seek.friend.util.Context.TokenIdContext;
import com.seek.friend.util.Exception.BizException;
import com.seek.friend.util.Exception.ErrorCodeEnum;
import com.seek.friend.util.FileUtil.FileSave;
import com.seek.friend.util.Redis.RedisUtil;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.model.chat.ChatModel;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RefreshScope
@Slf4j
@Service
public class AiFriendServiceImpl implements AiFriendService {

    private final CommonParamRulesConfig commonParamRulesConfig;
    private final AiFriendParamsRulesConfig aiFriendParamsRulesConfig;
    private final AiFriendRedisKeyConfig aiFriendRedisKeyConfig;
    private final ChatModel chatModel;
    private final RedisUtil redisUtil;
    private final IdUtil idUtil;
    private final AiFriendMapper aiFriendMapper;
    private final AiFriendCaffeine aiFriendCaffeine;
    private final RocketMQUtil rocketMQUtil;
    private final AiFriendTopic aiFriendTopic;
    private final CompleteFactory completeFactory;

    @Autowired
    public AiFriendServiceImpl(CommonParamRulesConfig commonParamRulesConfig,AiFriendParamsRulesConfig aiFriendParamsRulesConfig
            ,AiFriendRedisKeyConfig aiFriendRedisKeyConfig, ChatModel chatModel,RedisUtil redisUtil,IdUtil idUtil,AiFriendMapper aiFriendMapper
            ,AiFriendCaffeine aiFriendCaffeine,RocketMQUtil rocketMQUtil,AiFriendTopic aiFriendTopic,CompleteFactory completeFactory) {
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.aiFriendParamsRulesConfig = aiFriendParamsRulesConfig;
        this.aiFriendRedisKeyConfig = aiFriendRedisKeyConfig;
        this.chatModel = chatModel;
        this.redisUtil = redisUtil;
        this.idUtil = idUtil;
        this.aiFriendMapper = aiFriendMapper;
        this.aiFriendCaffeine = aiFriendCaffeine;
        this.rocketMQUtil = rocketMQUtil;
        this.aiFriendTopic = aiFriendTopic;
        this.completeFactory = completeFactory;
    }

    @PostConstruct
    public void initContext(){
        redisUtil.trySetString(aiFriendRedisKeyConfig.getAiFriendIdCount(),null,""+commonParamRulesConfig.getIdCapacity());
        FileSave.createDestDir(aiFriendParamsRulesConfig.getHeaderImageDest());
    }


    //初始化ai好友
    @Override
    public long init(String name){
        aiFriendParamsRulesConfig.nameCheck(name);
        long userId=quickCheckCooldownAndGetUserId(aiFriendRedisKeyConfig.getInitAiFriendCooldown());
        long aiFriendId=idUtil.IdGenerateByIncrease(aiFriendRedisKeyConfig.getAiFriendIdCount());
        aiFriendMapper.init(aiFriendId, name , userId);
        rocketMQUtil.send(aiFriendTopic.getTopicName(),aiFriendTopic.getChangeAiFriendAmount().getTag(),new ChangeAmountDTO(userId,1));
        return aiFriendId;
    }

    //更新ai好友的文字信息
    @Override
    public void updateText(AiFriendDTO aiFriend){
        //检查参数
        aiFriendParamsRulesConfig.nameCheck(aiFriend.getName());
        aiFriendParamsRulesConfig.descriptionCheck(aiFriend.getDescription());
        aiFriendParamsRulesConfig.hobbyCheck(aiFriend.getHobbies());
        aiFriendParamsRulesConfig.characteristicCheck(aiFriend.getCharacteristic());
        aiFriendParamsRulesConfig.encounterReasonCheck(aiFriend.getEncounterReason());
        aiFriendParamsRulesConfig.likeScoreCheck(aiFriend.getLikeScore());
        //检查冷却并获取Id,设置必要的参数
        aiFriend.setUserId(quickCheckCooldownAndGetUserId(aiFriendRedisKeyConfig.getUpdateAiFriendTextCooldown()));
        //插入DB并且清除缓存
        aiFriendCaffeine.updateAndRemoveCaffeine(aiFriend.getAiFriendId(),k->aiFriendMapper.updateText(aiFriend));
    }


    //更新ai好友的头像
    @Override
    public void updateHeader(MultipartFile file, long aiFriendId){
        //检查
        commonParamRulesConfig.commonIdCheck(aiFriendId);
        long userId=quickCheckCooldownAndGetUserId(aiFriendRedisKeyConfig.getUpdateAiFriendHeaderCooldown());
        //保存文件
        String addr= FileSave.quickCheckAndSaveFile(file,aiFriendParamsRulesConfig.getHeaderImageDest()
                ,commonParamRulesConfig.getImageSize(),commonParamRulesConfig.getImageType());
        //写入DB,通过List的长度来判断有没有扫到目标数据行
        List<String> oldAddr=aiFriendMapper.updateHeader(addr,aiFriendId,userId);
        if (oldAddr.isEmpty()){
            quickDeleteHeaderImage(addr);
            throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
        }
        quickDeleteHeaderImage(oldAddr.getFirst());
        //清除缓存
        aiFriendCaffeine.deleteAllCaffeine(aiFriendId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void complete(long aiFriendId){
        AiFriendDTO aiFriend=getDetail(aiFriendId);
        if (aiFriend.getComplete()==true)throw new BizException(ErrorCodeEnum.CONDITION_NOT_PASS);
        long userId=quickCheckCooldownAndGetUserId(aiFriendRedisKeyConfig.getCompleteAiFriendCooldown());
        SystemMessage systemMessage=completeFactory.getHistory(aiFriend);
        List<ChatMessage> msgs=List.of(systemMessage);
        String history=chatModel.chat(msgs).aiMessage().text();
        if (!aiFriendMapper.complete(history,aiFriendId,userId))throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
        //清除缓存
        aiFriendCaffeine.deleteAllCaffeine(aiFriendId);
        aiFriend.setCharacterHistory(history);
        rocketMQUtil.send(aiFriendTopic.getTopicName(),aiFriendTopic.getInitChatRoom().getTag(),aiFriend);
    }

    //批量获取预览
    @Override
    public List<AiFriendDTO> simpleGetList(int start, int need,Boolean complete){
        commonParamRulesConfig.needNumberCheck(need);
        long userId=quickCheckCooldownAndGetUserId(aiFriendRedisKeyConfig.getAiFriendSimpleGetListCooldown());
        return aiFriendMapper.simpleGetList(start,need,userId,complete);
    }


    //获取该ai好友的详细信息
    @Override
    public AiFriendDTO getDetail(long aiFriendId){
        commonParamRulesConfig.commonIdCheck(aiFriendId);
        long userId=quickGetUserId();
        AiFriendDTO aiFriend=aiFriendCaffeine.getAndAutoLoad(aiFriendId,k->aiFriendMapper.getDetail(k,userId));
        if (aiFriend==null||aiFriend.getUserId()!=userId)throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
        return aiFriend;
    }


    //删除ai好友
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(long aiFriendId){
        commonParamRulesConfig.commonIdCheck(aiFriendId);
        long userId=quickCheckCooldownAndGetUserId(aiFriendRedisKeyConfig.getAiFriendDeleteCooldown());
        //逻辑删除用户,并且获取其是否完成初始化的状态
        Boolean complete=aiFriendMapper.delete(aiFriendId,quickCheckCooldownAndGetUserId(aiFriendRedisKeyConfig.getAiFriendDeleteCooldown()));
        //发送销毁聊天室
        if (complete!=null&&complete) rocketMQUtil.send(aiFriendTopic.getTopicName(),aiFriendTopic.getDeleteChatRoom().getTag(),aiFriendId);
        rocketMQUtil.send(aiFriendTopic.getTopicName(),aiFriendTopic.getChangeAiFriendAmount().getTag(),new ChangeAmountDTO(userId,-1));
        //清除缓存
        aiFriendCaffeine.deleteAllCaffeine(aiFriendId);
    }



    private long quickCheckCooldownAndGetUserId(RedisKeyData key){
        long userId= quickGetUserId();
        redisUtil.checkCooldown(key,userId);
        return userId;
    }

    private long quickGetUserId(){
        return TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getIdCapacity());
    }

    private void quickDeleteHeaderImage(String addr){
        if (addr==null||addr.isEmpty())return;
        rocketMQUtil.send(aiFriendTopic.getTopicName(),aiFriendTopic.getDeleteFile().getTag()
                , Paths.get(aiFriendParamsRulesConfig.getHeaderImageDest(),addr).toString());
    }













}
