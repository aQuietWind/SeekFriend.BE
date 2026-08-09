package com.seek.friend.config.AutoConfig;

import com.seek.friend.config.NacosConfig.GatewayConfig.GatewayBlockConfig;
import com.seek.friend.config.NacosConfig.GatewayConfig.GatewayCaffeineConfig;
import com.seek.friend.config.NacosConfig.GatewayConfig.GatewayRedisKeyConfig;
import com.seek.friend.config.NacosConfig.GatewayConfig.GatewayRequestPathConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
// 绑定当前组件对应的属性类
@EnableConfigurationProperties({GatewayBlockConfig.class, GatewayCaffeineConfig.class, GatewayRedisKeyConfig.class, GatewayRequestPathConfig.class})
public class GatewayAutoConfig {
    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public GatewayBlockConfig gatewayBlockConfig(GatewayBlockConfig gatewayBlockConfig) {
        return gatewayBlockConfig;
    }

    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public GatewayCaffeineConfig gatewayCaffeineConfig(GatewayCaffeineConfig gatewayCaffeineConfig) {
        return gatewayCaffeineConfig;
    }

    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public GatewayRedisKeyConfig gatewayRedisKeyConfig(GatewayRedisKeyConfig gatewayRedisKeyConfig) {
        return gatewayRedisKeyConfig;
    }

    @Bean
    @Lazy // 用到才实例化，启动不创建对象
    public GatewayRequestPathConfig gatewayRequestPathConfig(GatewayRequestPathConfig gatewayRequestPathConfig) {
        return gatewayRequestPathConfig;
    }


























}
