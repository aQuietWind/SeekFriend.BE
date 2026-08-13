package com.seek.friend.aichat.Consumer;

import com.seek.friend.aichat.Mapper.AiFriendMapper;
import com.seek.friend.aichat.Mapper.RoomMapper;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;
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
@RocketMQMessageListener(consumerGroup = "aiFriendTopicInitChatRoomConsumer",
        topic = "aiFriendTopic",
        selectorExpression = "initChatRoom")
public class InitChatRoomConsumer implements RocketMQListener<AiFriendDTO> {

    private final RoomMapper roomMapper;
    private final AiFriendMapper aiFriendMapper;

    @Autowired
    public InitChatRoomConsumer(RoomMapper roomMapper, AiFriendMapper aiFriendMapper) {
        this.roomMapper = roomMapper;
        this.aiFriendMapper = aiFriendMapper;
    }

    @Override
    public void onMessage(AiFriendDTO aiFriend) {
        try {
            roomMapper.insert(aiFriend.getAiFriendId(),aiFriend.getUserId());
        } catch (DuplicateKeyException ignore){}
        aiFriendMapper.insert(aiFriend);
    }












}
