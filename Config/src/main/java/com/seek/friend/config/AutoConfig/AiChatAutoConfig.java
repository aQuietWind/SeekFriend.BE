package com.seek.friend.config.AutoConfig;

import com.seek.friend.config.NacosConfig.AiChat.AiChatCaffeineConfig;
import com.seek.friend.config.NacosConfig.AiChat.AiChatParamsRulesConfig;
import com.seek.friend.config.NacosConfig.AiChat.AiChatRedisKeyConfig;
import com.seek.friend.config.NacosConfig.AiFriend.AiFriendCaffeineConfig;
import com.seek.friend.config.NacosConfig.AiFriend.AiFriendParamsRulesConfig;
import com.seek.friend.config.NacosConfig.AiFriend.AiFriendRedisKeyConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
// 绑定当前组件对应的属性类
@EnableConfigurationProperties({AiChatParamsRulesConfig.class, AiChatRedisKeyConfig.class, AiChatCaffeineConfig.class})
public class AiChatAutoConfig {
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public AiChatParamsRulesConfig aiChatParamsRulesConfig(AiChatParamsRulesConfig aiChatParamsRulesConfig) {
        return aiChatParamsRulesConfig;
    }
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public AiChatRedisKeyConfig aiChatRedisKeyConfig(AiChatRedisKeyConfig aiChatRedisKeyConfig) {
        return aiChatRedisKeyConfig;
    }
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public AiChatCaffeineConfig aiChatCaffeineConfig(AiChatCaffeineConfig aiChatCaffeineConfig) {
        return aiChatCaffeineConfig;
    }












}
