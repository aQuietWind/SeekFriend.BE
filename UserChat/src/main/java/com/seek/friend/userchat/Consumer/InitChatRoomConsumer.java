package com.seek.friend.userchat.Consumer;

import com.seek.friend.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.friend.config.NacosConfig.UserChat.UserChatRedisKeyConfig;
import com.seek.friend.serviceobject.UserFriend.ChatConnectionMQDTO;
import com.seek.friend.userchat.Mapper.UserChatRoomMapper;
import com.seek.friend.util.CommonUtil.IdUtil;
import com.seek.friend.util.Redis.RedisUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
//可以用配置地址取代该常量配置,But,I am too lazy to do it!
//下次我还是更加喜欢RabbitMQ
@RocketMQMessageListener(consumerGroup = "userFriendTopicInitChatRoomConsumer",
        topic = "userFriendTopic",
        maxReconsumeTimes = 0,
        selectorExpression = "initChatRoom")
public class InitChatRoomConsumer implements RocketMQListener<ChatConnectionMQDTO> {

    private final UserChatRedisKeyConfig userChatRedisKeyConfig;
    private final RedisUtil redisUtil;
    private final UserChatRoomMapper userChatRoomMapper;
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final IdUtil idUtil;
    @Autowired
    public InitChatRoomConsumer(UserChatRedisKeyConfig userChatRedisKeyConfig,RedisUtil redisUtil,UserChatRoomMapper userChatRoomMapper
    ,CommonParamRulesConfig commonParamRulesConfig,IdUtil idUtil) {
        this.userChatRedisKeyConfig = userChatRedisKeyConfig;
        this.redisUtil = redisUtil;
        this.userChatRoomMapper = userChatRoomMapper;
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.idUtil = idUtil;
    }

    @PostConstruct
    public void init() {
        redisUtil.trySetString(userChatRedisKeyConfig.getRoomIdCount(),null,""+commonParamRulesConfig.getIdCapacity());
    }

    @Override
    public void onMessage(ChatConnectionMQDTO data){
        //用版本号实现幂等效果，即，无论如何重试，什么意味，结果总是往更加新鲜的方向进行同步，直到两边结果完全一致
        if (userChatRoomMapper.updateAbleChat(data.getConnectionId(), true, data.getVersion()))return;
        try {
            //如果是由于重试导致的更新失败,使这里出现多消费者并发问题，那么在此处就会导致插入时唯一键冲突，抛出唯一冲突异常
            userChatRoomMapper.insertChatRoom(idUtil.IdGenerateByIncrease(userChatRedisKeyConfig.getRoomIdCount())
                    ,data.getConnectionId(),data.getFirstUserId(),  data.getSecondUserId());
        }catch (DuplicateKeyException ignore){}
        catch (Exception e){
            log.error("插入聊天室时出现异常:{}",e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }












}
