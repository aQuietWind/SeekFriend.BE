package com.seek.friend.userchat.Consumer;

import com.seek.friend.util.FileUtil.FileRemove;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
//可以用配置地址取代该常量配置,But,I am too lazy to do it!
//下次我还是更加喜欢RabbitMQ
@RocketMQMessageListener(consumerGroup = "userFriendTopicDeleteFileConsumer",
        topic = "userFriendTopic",
        selectorExpression = "deleteFile")
public class DeleteFileConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String path){
        FileRemove.removeFileByPath(path);
    }












}
