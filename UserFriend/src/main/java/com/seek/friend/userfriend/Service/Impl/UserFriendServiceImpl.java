package com.seek.friend.userfriend.Service.Impl;

import com.seek.friend.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.friend.config.NacosConfig.UserFriend.UserFriendRedisKeyConfig;
import com.seek.friend.serviceobject.UserFriend.UserFriendConnectionDTO;
import com.seek.friend.userfriend.Mapper.UserFriendMapper;
import com.seek.friend.userfriend.Service.UserFriendService;
import com.seek.friend.util.Context.TokenIdContext;
import com.seek.friend.util.Redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RefreshScope
public class UserFriendServiceImpl implements UserFriendService {

    private final CommonParamRulesConfig commonParamRulesConfig;
    private final RedisUtil redisUtil;
    private final UserFriendRedisKeyConfig userFriendRedisKeyConfig;
    private final UserFriendMapper userFriendMapper;
    @Autowired
    public UserFriendServiceImpl(CommonParamRulesConfig commonParamRulesConfig, RedisUtil redisUtil, UserFriendRedisKeyConfig userFriendRedisKeyConfig
    , UserFriendMapper userFriendMapper) {
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.redisUtil = redisUtil;
        this.userFriendRedisKeyConfig = userFriendRedisKeyConfig;
        this.userFriendMapper = userFriendMapper;
    }

    @Override
    public void applyFriend(long userId){
        commonParamRulesConfig.userIdCheck(userId);
        long ownId= TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getIdCapacity());
        redisUtil.checkCooldown(userFriendRedisKeyConfig.getApplyConnectionCooldown(),userId);
        userFriendMapper.applyFriend(ownId,userId);
    }

    @Override
    public List<UserFriendConnectionDTO> getApplicantList(int start, int need){
        commonParamRulesConfig.needNumberCheck(need);
        long userId=quickGetUserId();
        redisUtil.checkCooldown(userFriendRedisKeyConfig.getGetApplicantListCooldown(),userId);
        return userFriendMapper.getApplicantList(start,need,userId);
    }

    @Override
    public List<UserFriendConnectionDTO> getRespondentList(int start, int need){
        commonParamRulesConfig.needNumberCheck(need);
        long userId=quickGetUserId();
        redisUtil.checkCooldown(userFriendRedisKeyConfig.getGetRespondentListCooldown(),userId);
        return userFriendMapper.getRespondentList(start,need,userId);
    }

    @Override
    public List<UserFriendConnectionDTO> getFriendList(int start, int need){
        commonParamRulesConfig.needNumberCheck(need);
        long userId=quickGetUserId();
        redisUtil.checkCooldown(userFriendRedisKeyConfig.getGetFriendListCooldown(),userId);
        return userFriendMapper.getFriendList(start,need,userId);
    }

    @Override
    public void respondApplication(long connectionId,boolean value){
        commonParamRulesConfig.commonIdCheck(connectionId);
        long userId=quickGetUserId();
        redisUtil.checkCooldown(userFriendRedisKeyConfig.getRespondApplicationCooldown(),userId);
        userFriendMapper.respondApplication(connectionId,value,userId);
    }

    @Override
    public void deleteFriend(long connectionId){
        commonParamRulesConfig.commonIdCheck(connectionId);
        long userId=quickGetUserId();
        redisUtil.checkCooldown(userFriendRedisKeyConfig.getDeleteConnectionCooldown(),userId);
        userFriendMapper.deleteFriend(connectionId,userId);
    }

    private long quickGetUserId(){
        return TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getIdCapacity());
    }
}
