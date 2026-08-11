package com.seek.friend.config.AutoConfig;

import com.seek.friend.config.NacosConfig.AiFriend.AiFriendParamsRulesConfig;
import com.seek.friend.config.NacosConfig.AiFriend.AiFriendRedisKeyConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
// 绑定当前组件对应的属性类
@EnableConfigurationProperties({AiFriendParamsRulesConfig.class, AiFriendRedisKeyConfig.class})
public class AiFriendAutoConfig {
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public AiFriendParamsRulesConfig aiFriendParamsRulesConfig(AiFriendParamsRulesConfig aiFriendParamsRulesConfig) {
        return aiFriendParamsRulesConfig;
    }
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public AiFriendRedisKeyConfig aiFriendRedisKeyConfig(AiFriendRedisKeyConfig aiFriendRedisKeyConfig) {
        return aiFriendRedisKeyConfig;
    }












}
