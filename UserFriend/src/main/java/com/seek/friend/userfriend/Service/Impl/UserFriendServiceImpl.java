package com.seek.friend.userfriend.Service.Impl;

import com.seek.friend.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.friend.config.NacosConfig.RocketMQBindConfig.UserFriendTopic;
import com.seek.friend.config.NacosConfig.UserFriend.UserFriendRedisKeyConfig;
import com.seek.friend.mqutil.RocketMQ.RocketMQUtil;
import com.seek.friend.serviceobject.UserFriend.UserFriendConnectionDTO;
import com.seek.friend.serviceobject.UserFriend.UserFriendMQConnectionDTO;
import com.seek.friend.userfriend.Mapper.UserFriendMapper;
import com.seek.friend.userfriend.Service.UserFriendService;
import com.seek.friend.util.CommonUtil.IdUtil;
import com.seek.friend.util.Context.TokenIdContext;
import com.seek.friend.util.Exception.BizException;
import com.seek.friend.util.Exception.ErrorCodeEnum;
import com.seek.friend.util.Redis.RedisUtil;
import jakarta.annotation.PostConstruct;
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
    private final RocketMQUtil rocketMQUtil;
    private final UserFriendTopic userFriendTopic;
    private final IdUtil idUtil;
    @Autowired
    public UserFriendServiceImpl(CommonParamRulesConfig commonParamRulesConfig, RedisUtil redisUtil, UserFriendRedisKeyConfig userFriendRedisKeyConfig
    , UserFriendMapper userFriendMapper, RocketMQUtil rocketMQUtil, UserFriendTopic userFriendTopic, IdUtil idUtil) {
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.redisUtil = redisUtil;
        this.userFriendRedisKeyConfig = userFriendRedisKeyConfig;
        this.userFriendMapper = userFriendMapper;
        this.rocketMQUtil = rocketMQUtil;
        this.userFriendTopic = userFriendTopic;
        this.idUtil = idUtil;
    }
    @PostConstruct
    public void init(){
        redisUtil.trySetString(userFriendRedisKeyConfig.getConnectionIdCount(),null,""+commonParamRulesConfig.getIdCapacity());
    }

    @Override
    public void applyFriend(long userId){
        commonParamRulesConfig.userIdCheck(userId);
        long ownId= TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getIdCapacity());
        redisUtil.checkCooldown(userFriendRedisKeyConfig.getApplyConnectionCooldown(),userId);
        Long connectionId=userFriendMapper.getConnectionIdByUser(ownId,userId);
        if (connectionId==null){
            userFriendMapper.insertFriendApplication(idUtil.IdGenerateByIncrease(userFriendRedisKeyConfig.getConnectionIdCount()),ownId,userId);
        }else {
            userFriendMapper.applyFriend(connectionId,userId);
        }

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
        if (value){
            rocketMQUtil.send(userFriendTopic.getTopicName()
                    ,userFriendTopic.getInitChatRoom().getTag()
                    ,new UserFriendMQConnectionDTO(connectionId,userId,userFriendMapper.getApplicantUserId(connectionId,userId)));
        }
    }

    @Override
    public void deleteFriend(long connectionId){
        commonParamRulesConfig.commonIdCheck(connectionId);
        long userId=quickGetUserId();
        redisUtil.checkCooldown(userFriendRedisKeyConfig.getDeleteConnectionCooldown(),userId);
        userFriendMapper.deleteFriend(connectionId,userId);
        rocketMQUtil.send(userFriendTopic.getTopicName(),userFriendTopic.getDeleteChatRoom().getTag(),connectionId);
    }

    private long quickGetUserId(){
        return TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getIdCapacity());
    }
}
