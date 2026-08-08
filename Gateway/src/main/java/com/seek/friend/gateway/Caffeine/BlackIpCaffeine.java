package com.seek.friend.gateway.Caffeine;

import com.seek.food.config.NacosConfig.Gateway.GatewayBlackConfig;
import com.seek.food.util.Caffeine.JvmCaffeineParent;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class BlackIpCaffeine extends JvmCaffeineParent<String,Long> {

    // 构造注入配置
    private final GatewayBlackConfig gatewayBlackConfig;

    @Autowired
    public BlackIpCaffeine(GatewayBlackConfig gatewayBlackConfig) {
        this.gatewayBlackConfig = gatewayBlackConfig;
    }

    // 容器启动构建缓存
    @PostConstruct
    public void init() {
        super.CACHE = com.seek.friend.gateway.Caffeine.newBuilder()
                .maximumSize(gatewayBlackConfig.getIp().getCaffeineMaxSize())
                .expireAfterWrite(gatewayBlackConfig.getIp().getCaffeineExpireTime(), TimeUnit.MINUTES)
                .recordStats()
                .build();
    }

    // 容器销毁清理缓存
    @PreDestroy
    public void destroy() {
        super.CACHE.cleanUp();
        super.CACHE.invalidateAll();
    }
}
