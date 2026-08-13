package com.seek.friend.aichat.Service.Impl;

import com.seek.friend.aichat.Mapper.RoomMapper;
import com.seek.friend.aichat.Service.ChatRoomService;
import com.seek.friend.config.NacosConfig.AiChat.AiChatRedisKeyConfig;
import com.seek.friend.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.friend.serviceobject.AiChat.ChatRoomDTO;
import com.seek.friend.util.Context.TokenIdContext;
import com.seek.friend.util.Redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;

@RefreshScope
@Service
@Slf4j
public class ChatRoomServiceImpl implements ChatRoomService {

    private final RoomMapper roomMapper;
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final RedisUtil redisUtil;
    private final AiChatRedisKeyConfig aiChatRedisKeyConfig;

    @Autowired
    public ChatRoomServiceImpl(RoomMapper roomMapper, CommonParamRulesConfig commonParamRulesConfig
            , RedisUtil redisUtil, AiChatRedisKeyConfig aiChatRedisKeyConfig) {
        this.roomMapper = roomMapper;
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.redisUtil = redisUtil;
        this.aiChatRedisKeyConfig = aiChatRedisKeyConfig;
    }

    @Override
    public List<ChatRoomDTO> getList(int start, int need){
        commonParamRulesConfig.needNumberCheck(need);
        long userId= TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getIdCapacity());
        redisUtil.checkCooldown(aiChatRedisKeyConfig.getRoomGetListCooldown(),userId);
        return roomMapper.getList(start,need,userId);
    }
}
