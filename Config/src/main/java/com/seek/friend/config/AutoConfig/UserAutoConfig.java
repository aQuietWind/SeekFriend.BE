package com.seek.friend.config.AutoConfig;

import com.seek.friend.config.NacosConfig.User.UserCaffeineConfig;
import com.seek.friend.config.NacosConfig.User.UserParamsRulesConfig;
import com.seek.friend.config.NacosConfig.User.UserRedisKeyConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
// 绑定当前组件对应的属性类
@EnableConfigurationProperties({UserParamsRulesConfig.class,UserCaffeineConfig.class,UserRedisKeyConfig.class})
public class UserAutoConfig {
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public UserParamsRulesConfig userParamsRulesConfig(UserParamsRulesConfig userParamsRulesConfig) {
        return userParamsRulesConfig;
    }
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public UserCaffeineConfig userCaffeineConfig(UserCaffeineConfig userCaffeineConfig) {
        return userCaffeineConfig;
    }
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public UserRedisKeyConfig userRedisKeyConfig(UserRedisKeyConfig userRedisKeyConfig) {
        return userRedisKeyConfig;
    }












}
