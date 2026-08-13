package com.seek.friend.aichat.Service.Impl;

import com.seek.friend.aichat.Caffeine.AiFriendCaffeine;
import com.seek.friend.aichat.Mapper.AiFriendMapper;
import com.seek.friend.aichat.Mapper.RecordMapper;
import com.seek.friend.aichat.Mapper.RoomMapper;
import com.seek.friend.aichat.Service.ChatRecordService;
import com.seek.friend.config.NacosConfig.AiChat.AiChatRedisKeyConfig;
import com.seek.friend.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.friend.serviceobject.AiChat.ChatRecordDTO;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;
import com.seek.friend.util.Context.TokenIdContext;
import com.seek.friend.util.Exception.BizException;
import com.seek.friend.util.Exception.ErrorCodeEnum;
import com.seek.friend.util.Redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RefreshScope
@Slf4j
@Service
public class ChatRecordServiceImpl implements ChatRecordService {
    private static final String record="";

    private final AiFriendCaffeine aiFriendCaffeine;
    private final AiFriendMapper aiFriendMapper;
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final RedisUtil redisUtil;
    private final AiChatRedisKeyConfig aiChatRedisKeyConfig;
    private final RecordMapper recordMapper;
    private final RoomMapper roomMapper;

    @Autowired
    public ChatRecordServiceImpl(AiFriendCaffeine aiFriendCaffeine , AiFriendMapper aiFriendMapper , CommonParamRulesConfig commonParamRulesConfig
    , RedisUtil redisUtil , AiChatRedisKeyConfig aiChatRedisKeyConfig, RecordMapper recordMapper, RoomMapper roomMapper) {
        this.aiFriendCaffeine = aiFriendCaffeine;
        this.aiFriendMapper = aiFriendMapper;
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.redisUtil = redisUtil;
        this.aiChatRedisKeyConfig = aiChatRedisKeyConfig;
        this.recordMapper = recordMapper;
        this.roomMapper = roomMapper;
    }

    @Override
    public String chat(String description, MultipartFile file, long aiFriendId){
        long userId=TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getUserIdStart());
        //查冷却,防脚本
        redisUtil.checkCooldown(aiChatRedisKeyConfig.getRecordInsertCooldown(),userId);
        //判断是否符合条件
        if (!roomMapper.exist(aiFriendId,userId))throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
        //获取
        AiFriendDTO aiFriend= quickGetAiFriend(aiFriendId,userId);
        //获取会话记忆
    }

    //前端主动希望ai挑起话题
    @Override
    public String initiativeChat(long aiFriendId){
        return "";
    }

    @Override
    public List<ChatRecordDTO> getList(int start, int need, long aiFriendId){
        commonParamRulesConfig.commonIdCheck(aiFriendId);
        commonParamRulesConfig.needNumberCheck(need);
        long userId= TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getIdCapacity());
        redisUtil.checkCooldown(aiChatRedisKeyConfig.getRecordGetListCooldown(), userId);
        return recordMapper.getList(start,need,aiFriendId,userId);
    }























    private AiFriendDTO quickGetAiFriend(long aiFriendId,long userId){
        AiFriendDTO aiFriend=aiFriendCaffeine.getAndAutoLoad(aiFriendId,k->aiFriendMapper.getDetail(aiFriendId));
        if (aiFriend==null||aiFriend.getUserId()!=userId)throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
        return aiFriend;
    }


}
