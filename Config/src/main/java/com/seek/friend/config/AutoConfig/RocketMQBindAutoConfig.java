package com.seek.friend.config.AutoConfig;

import com.seek.friend.config.NacosConfig.RocketMQBindConfig.UserTopic;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
// 绑定当前组件对应的属性类
@EnableConfigurationProperties({UserTopic.class})
public class RocketMQBindAutoConfig {
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public UserTopic userTopic(UserTopic userTopic) {
        return userTopic;
    }












}
