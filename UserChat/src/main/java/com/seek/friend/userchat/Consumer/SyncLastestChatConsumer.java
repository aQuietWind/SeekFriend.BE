package com.seek.friend.userchat.Consumer;

import com.seek.friend.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.friend.config.NacosConfig.UserChat.UserChatRedisKeyConfig;
import com.seek.friend.serviceobject.UserFriend.ChatConnectionMQDTO;
import com.seek.friend.userchat.Mapper.UserChatRoomMapper;
import com.seek.friend.util.Redis.RedisUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
//可以用配置地址取代该常量配置,But,I am too lazy to do it!
//下次我还是更加喜欢RabbitMQ
@RocketMQMessageListener(consumerGroup = "userFriendTopicSyncLastestChatTimeConsumer",
        topic = "userChatTopic",
        selectorExpression = "syncLastestChatTimeRoom")
public class SyncLastestChatConsumer implements RocketMQListener<Long> {

    private final UserChatRedisKeyConfig userChatRedisKeyConfig;
    private final RedisUtil redisUtil;
    private final UserChatRoomMapper userChatRoomMapper;
    private final CommonParamRulesConfig commonParamRulesConfig;
    @Autowired
    public SyncLastestChatConsumer(UserChatRedisKeyConfig userChatRedisKeyConfig,RedisUtil redisUtil,UserChatRoomMapper userChatRoomMapper
            ,CommonParamRulesConfig commonParamRulesConfig) {
        this.userChatRedisKeyConfig = userChatRedisKeyConfig;
        this.redisUtil = redisUtil;
        this.userChatRoomMapper = userChatRoomMapper;
        this.commonParamRulesConfig = commonParamRulesConfig;
    }

    @PostConstruct
    public void init() {
        redisUtil.trySetString(userChatRedisKeyConfig.getRoomIdCount(),null,""+commonParamRulesConfig.getIdCapacity());
    }

    @Override
    public void onMessage(Long roomId){
        userChatRoomMapper.syncLastestChatTime(roomId);
    }












}
