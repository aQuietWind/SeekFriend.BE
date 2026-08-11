package com.seek.friend.userchat.Consumer;


import com.seek.friend.serviceobject.UserFriend.ChatConnectionMQDTO;
import com.seek.friend.userchat.WebSocketServer.WebSocketServer.ChatInformServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
//可以用配置地址取代该常量配置,But,I am too lazy to do it!
//下次我还是更加喜欢RabbitMQ
@RocketMQMessageListener(consumerGroup = "userChatTopicChatInformConsumer",
        topic = "userFriendTopic",
        selectorExpression = "insertChatRecord")
public class ChatInformConsumer implements RocketMQListener<ChatConnectionMQDTO> {

    private final ChatInformServer chatInformServer;
    @Autowired
    public ChatInformConsumer(ChatInformServer chatInformServer) {
        this.chatInformServer = chatInformServer;
    }

    @Override
    public void onMessage(ChatConnectionMQDTO data){

    }












}
