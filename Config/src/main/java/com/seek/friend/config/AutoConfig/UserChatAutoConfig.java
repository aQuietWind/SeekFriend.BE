package com.seek.friend.config.AutoConfig;

import com.seek.friend.config.NacosConfig.UserChat.UserChatParamsRulesConfig;
import com.seek.friend.config.NacosConfig.UserChat.UserChatRedisKeyConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
// 绑定当前组件对应的属性类
@EnableConfigurationProperties({UserChatParamsRulesConfig.class, UserChatRedisKeyConfig.class})
public class UserChatAutoConfig {
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public UserChatParamsRulesConfig userChatParamsRulesConfig(UserChatParamsRulesConfig userChatParamsRulesConfig) {
        return userChatParamsRulesConfig;
    }
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public UserChatRedisKeyConfig userChatRedisKeyConfig(UserChatRedisKeyConfig userChatRedisKeyConfig) {
        return userChatRedisKeyConfig;
    }












}
