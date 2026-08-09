package com.seek.friend.mqutil.AutoConfig;

import com.seek.friend.mqutil.RocketMQ.RocketMQUtil;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilAutoConfig {
    @Bean
    public RocketMQUtil rocketMQUtil(RocketMQTemplate rocketMQTemplate){
        return new RocketMQUtil(rocketMQTemplate);
    }
}
