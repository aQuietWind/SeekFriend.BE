package com.seek.friend.userchat.Service.Impl;

import com.seek.friend.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.friend.config.NacosConfig.RocketMQBindConfig.UserChatTopic;
import com.seek.friend.config.NacosConfig.UserChat.UserChatParamsRulesConfig;
import com.seek.friend.config.NacosConfig.UserChat.UserChatRedisKeyConfig;
import com.seek.friend.configobject.RedisData.RedisKeyData;
import com.seek.friend.mqutil.RocketMQ.RocketMQUtil;
import com.seek.friend.serviceobject.UserChat.ChatRecordDTO;
import com.seek.friend.userchat.Mapper.UserChatRecordMapper;
import com.seek.friend.userchat.Service.UserChatRecordService;
import com.seek.friend.util.CommonUtil.IdUtil;
import com.seek.friend.util.Context.TokenIdContext;
import com.seek.friend.util.Exception.BizException;
import com.seek.friend.util.Exception.ErrorCodeEnum;
import com.seek.friend.util.FileUtil.FileSave;
import com.seek.friend.util.Redis.RedisUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RefreshScope
public class UserChatRecordServiceImpl implements UserChatRecordService {

    private final StringRedisTemplate stringRedisTemplate;
    private final UserChatRedisKeyConfig userChatRedisKeyConfig;
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final UserChatParamsRulesConfig userChatParamsRulesConfig;
    private final UserChatRecordMapper userChatRecordMapper;
    private final RedisUtil redisUtil;
    private final RocketMQUtil rocketMQUtil;
    private final UserChatTopic userChatTopic;
    private final IdUtil idUtil;

    @Autowired
    public UserChatRecordServiceImpl(StringRedisTemplate stringRedisTemplate, UserChatRedisKeyConfig userChatRedisKeyConfig
            , CommonParamRulesConfig commonParamRulesConfig, UserChatParamsRulesConfig userChatParamsRulesConfig
            , UserChatRecordMapper userChatRecordMapper, RedisUtil redisUtil, RocketMQUtil rocketMQUtil, UserChatTopic userChatTopic, IdUtil idUtil) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.userChatRedisKeyConfig = userChatRedisKeyConfig;
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.userChatParamsRulesConfig = userChatParamsRulesConfig;
        this.userChatRecordMapper = userChatRecordMapper;
        this.redisUtil = redisUtil;
        this.rocketMQUtil = rocketMQUtil;
        this.userChatTopic = userChatTopic;
        this.idUtil = idUtil;
    }

    @PostConstruct
    public void init() {
        redisUtil.trySetString(userChatRedisKeyConfig.getRecordIdCount(),null,""+commonParamRulesConfig.getIdCapacity());
        FileSave.createDestDir(userChatParamsRulesConfig.getChatRecordImageDest());
    }
    //插入聊天记录
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(String description, MultipartFile file, long roomId) throws MQBrokerException, RemotingException, UnsupportedEncodingException, InterruptedException, MQClientException {
        //校验参数
        userChatParamsRulesConfig.descriptionCheck(description);
        commonParamRulesConfig.commonIdCheck(roomId);
        //获取tokenId,并且检测冷却
        long userId=quickGetIdAndCheckCooldown(userChatRedisKeyConfig.getRecordInsertCooldown());
        //检查是否存在该关联
        chatRoomService.checkIdAndRoom(roomId,user);
        //先保存文件
        String addr=null;
        if (file!=null&&!file.isEmpty())addr=quickSaveRecordImage(file);
        //延时删除该图片
        if (addr!=null)quickDeleteFileDelay(addr);
        //初始化聊天记录
        ChatRecordDTO record=new ChatRecordDTO( idUtil.IdGenerateByIncrease(userChatRedisKeyConfig.getRecordIdCount())
                , roomId, userId, description, addr
                , LocalDateTime.now().plusSeconds(userChatParamsRulesConfig.getRecordAbleWithdrawSeconds())
                , null);
        userChatRecordMapper.insert(record);
        //通知WebSocket有新的聊天记录
        quickSend(chatExchangeConfig.getChatInformQueue().getRoutingKey(),roomId);
    }

    //批量查询聊天记录
    @Override
    public List<ChatRecordDTO> getList(int start, int need, long roomId){
        //检查参数
        commonParamRulesConfig.commonIdCheck(roomId);
        commonParamRulesConfig.needNumberCheck(need);
        //检查冷却并且获取tokenId
        long userId=quickGetIdAndCheckCooldown(userChatRedisKeyConfig.getRecordGetListCooldown());
        return userChatRecordMapper.getList(start,need,roomId,userId);
    }

    //撤回聊天记录
    @Override
    public void withdraw(long recordId){
        //检查参数
        commonParamRulesConfig.commonIdCheck(recordId);
        //检查冷却并且获取tokenId
        long userId=quickGetIdAndCheckCooldown(userChatRedisKeyConfig.getRecordWithdrawCooldown());
        //尝试撤回
        if (!userChatRecordMapper.withdraw(recordId,userId)) throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
    }


    private long quickGetIdAndCheckCooldown(RedisKeyData key){
        long userId=TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getIdCapacity());
        redisUtil.checkCooldown(key,userId);
        return userId;
    }

    private String quickSaveRecordImage(MultipartFile file){
        return FileSave.quickCheckAndSaveFile(file, userChatParamsRulesConfig.getChatRecordImageDest(),commonParamRulesConfig.getImageSize()
                ,commonParamRulesConfig.getImageType());
    }

    private void quickDeleteFileDelay(String addr) throws MQBrokerException, RemotingException, UnsupportedEncodingException, InterruptedException, MQClientException {
        rocketMQUtil.sendDelay(userChatTopic.getTopicName(),userChatTopic.getDeleteFile().getTag(),
                Paths.get(userChatParamsRulesConfig.getChatRecordImageDest(),addr).toString(),userChatParamsRulesConfig.getFileDeleteDelaySeconds());
    }
}
