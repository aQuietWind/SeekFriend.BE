package com.seek.friend.aichat.Consumer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.seek.friend.aichat.WebSocketServer.ChatInformServer;
import com.seek.friend.serviceobject.Common.Result;
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
@RocketMQMessageListener(consumerGroup = "aiChatTopicChatInformConsumer-"+"${aichat.self.server-id}",
        topic = "aiChatTopic",
        selectorExpression = "insertChatRecord")
public class ChatInformConsumer implements RocketMQListener<Long> {

    private final ChatInformServer chatInformServer;
    private static final ObjectMapper objectMapper=new ObjectMapper();
    @Autowired
    public ChatInformConsumer(ChatInformServer chatInformServer) {
        this.chatInformServer = chatInformServer;
    }

    @Override
    public void onMessage(Long roomId){
        try {
            chatInformServer.broadcastRoomId(roomId,objectMapper.writeValueAsString(Result.success(roomId)));
        } catch (IOException e) {
            log.error("消费者广播聊天消息时出现异常",e);
        }
    }












}
