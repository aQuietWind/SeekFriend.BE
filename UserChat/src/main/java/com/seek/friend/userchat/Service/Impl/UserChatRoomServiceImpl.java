package com.seek.friend.userchat.Service.Impl;

import com.seek.friend.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.friend.config.NacosConfig.UserChat.UserChatRedisKeyConfig;
import com.seek.friend.configobject.RedisData.RedisKeyData;
import com.seek.friend.serviceobject.UserChat.ChatRoomDTO;
import com.seek.friend.userchat.Mapper.UserChatRoomMapper;
import com.seek.friend.userchat.Service.UserChatRoomService;
import com.seek.friend.util.Context.TokenIdContext;
import com.seek.friend.util.Exception.BizException;
import com.seek.friend.util.Exception.ErrorCodeEnum;
import com.seek.friend.util.Redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RefreshScope
public class UserChatRoomServiceImpl implements UserChatRoomService {

    private final CommonParamRulesConfig commonParamRulesConfig;
    private final RedisUtil redisUtil;
    private final UserChatRedisKeyConfig userChatRedisKeyConfig;
    private final UserChatRoomMapper userChatRoomMapper;
    @Autowired
    public UserChatRoomServiceImpl(CommonParamRulesConfig commonParamRulesConfig, RedisUtil redisUtil
            , UserChatRedisKeyConfig userChatRedisKeyConfig, UserChatRoomMapper userChatRoomMapper) {
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.redisUtil = redisUtil;
        this.userChatRedisKeyConfig = userChatRedisKeyConfig;
        this.userChatRoomMapper = userChatRoomMapper;
    }

    @Override
    public List<ChatRoomDTO> getList(int start, int need){
        commonParamRulesConfig.needNumberCheck(need);
        return userChatRoomMapper.getList(start,need,quickGetIdAndCheckCooldown(userChatRedisKeyConfig.getRoomGetListCooldown()));
    }

    private long quickGetIdAndCheckCooldown(RedisKeyData key){
        long userId= TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getIdCapacity());
        redisUtil.checkCooldown(key, userId);
        return userId;
    }
}
