package com.seek.friend.userchat.Consumer;


import com.seek.friend.serviceobject.UserFriend.ChatConnectionMQDTO;
import com.seek.friend.userchat.WebSocketServer.WebSocketServer.ChatInformServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
//可以用配置地址取代该常量配置,But,I am too lazy to do it!
//下次我还是更加喜欢RabbitMQ
@RocketMQMessageListener(consumerGroup = "userChatTopicChatInformConsumer-"+"${userchat.self.server-id}",
        topic = "userFriendTopic",
        selectorExpression = "insertChatRecord")
public class ChatInformConsumer implements RocketMQListener<Long> {

    private final ChatInformServer chatInformServer;
    @Autowired
    public ChatInformConsumer(ChatInformServer chatInformServer) {
        this.chatInformServer = chatInformServer;
    }

    @Override
    public void onMessage(Long roomId){
        try {
            chatInformServer.broadcastRoomId(roomId,"有新的消息");
        } catch (IOException e) {
            log.error("消费者广播聊天消息时出现异常",e);
        }
    }












}
