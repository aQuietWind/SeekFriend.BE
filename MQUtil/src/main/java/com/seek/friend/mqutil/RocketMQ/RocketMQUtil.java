package com.seek.friend.mqutil.RocketMQ;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;

public class RocketMQUtil {
    private final RocketMQTemplate rocketMQTemplate;
    @Autowired
    public RocketMQUtil(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    public void send(String topic,String tag, Object message){
        rocketMQTemplate.syncSend(getDestination(topic,tag), message);
    }

    public void sendDelay(String topic,String tag, String message,long timeout,int level){
        rocketMQTemplate.syncSend(getDestination(topic,tag), MessageBuilder.withPayload(message).build(),timeout,level);
    }

    public String getDestination(String topic,String tag){
        return topic+":"+tag;
    }
}
