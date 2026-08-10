package com.seek.friend.config.AutoConfig;

import com.seek.friend.config.NacosConfig.User.UserCaffeineConfig;
import com.seek.friend.config.NacosConfig.User.UserParamsRulesConfig;
import com.seek.friend.config.NacosConfig.User.UserRedisKeyConfig;
import com.seek.friend.config.NacosConfig.UserFriend.UserFriendParamsRulesConfig;
import com.seek.friend.config.NacosConfig.UserFriend.UserFriendRedisKeyConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
// 绑定当前组件对应的属性类
@EnableConfigurationProperties({UserFriendParamsRulesConfig.class, UserFriendRedisKeyConfig.class})
public class UserFriendAutoConfig {
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public UserFriendParamsRulesConfig userFriendParamsRulesConfig(UserFriendParamsRulesConfig userFriendParamsRulesConfig) {
        return userFriendParamsRulesConfig;
    }
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public UserFriendRedisKeyConfig userFriendRedisKeyConfig(UserFriendRedisKeyConfig userFriendRedisKeyConfig) {
        return userFriendRedisKeyConfig;
    }












}
