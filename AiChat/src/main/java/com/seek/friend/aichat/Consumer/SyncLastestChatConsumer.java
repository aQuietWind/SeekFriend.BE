package com.seek.friend.aichat.Consumer;

import com.seek.friend.aichat.Mapper.RoomMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
//可以用配置地址取代该常量配置,But,I am too lazy to do it!
//下次我还是更加喜欢RabbitMQ
@RocketMQMessageListener(consumerGroup = "aiFriendTopicSyncLastestChatTimeConsumer",
        topic = "aiChatTopic",
        selectorExpression = "insertChatRecord")
public class SyncLastestChatConsumer implements RocketMQListener<Long> {

    private final RoomMapper roomMapper;

    @Autowired
    public SyncLastestChatConsumer(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }

    @Override
    public void onMessage(Long aiFriendId) {
        roomMapper.syncChatTime(aiFriendId);
    }












}
