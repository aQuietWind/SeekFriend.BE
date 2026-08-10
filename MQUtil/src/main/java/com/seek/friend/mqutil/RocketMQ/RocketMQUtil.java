package com.seek.friend.mqutil.RocketMQ;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;

import java.io.UnsupportedEncodingException;

public class RocketMQUtil {
    private final RocketMQTemplate rocketMQTemplate;
    @Autowired
    public RocketMQUtil(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    public void send(String topic,String tag, Object message){
        rocketMQTemplate.syncSend(getDestination(topic,tag), message);
    }

    public void sendDelay(String topic,String tag, Object message,long delaySeconds) throws UnsupportedEncodingException, MQBrokerException, RemotingException, InterruptedException, MQClientException {
        Message msg=new Message(topic,tag,message.toString().getBytes(RemotingHelper.DEFAULT_CHARSET));
        msg.setDelayTimeSec(delaySeconds);
        rocketMQTemplate.getProducer().send(msg);
    }

    public String getDestination(String topic,String tag){
        return topic+":"+tag;
    }
}
