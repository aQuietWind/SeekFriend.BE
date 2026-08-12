package com.seek.friend.gateway.Caffeine;

import com.seek.friend.config.NacosConfig.GatewayConfig.GatewayCaffeineConfig;
import com.seek.friend.config.NacosConfig.GatewayConfig.GatewayRedisKeyConfig;
import com.seek.friend.util.Caffeine.JvmCaffeineParent;
import com.seek.friend.util.Redis.RedisUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class BlackIdCaffeine extends JvmCaffeineParent<String,Long> {

    // 构造注入配置
    private final GatewayCaffeineConfig gatewayCaffeineConfig;
    @Autowired
    public BlackIdCaffeine(GatewayCaffeineConfig gatewayCaffeineConfig, RedisUtil redisUtil, GatewayRedisKeyConfig gatewayRedisKeyConfig) {
        super(redisUtil,Long.class,gatewayRedisKeyConfig.getIdBlock());
        this.gatewayCaffeineConfig = gatewayCaffeineConfig;
    }

    // 容器启动构建缓存
    @PostConstruct
    public void init() {
        super.defaultInit(gatewayCaffeineConfig.getIdBlock());
    }

    // 容器销毁清理缓存
    @PreDestroy
    public void destroy() {
        super.CACHE.cleanUp();
        super.CACHE.invalidateAll();
    }
}
