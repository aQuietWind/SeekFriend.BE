package com.seek.friend.aichat.Caffeine;

import com.seek.friend.config.NacosConfig.AiFriend.AiFriendCaffeineConfig;
import com.seek.friend.config.NacosConfig.AiFriend.AiFriendRedisKeyConfig;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;
import com.seek.friend.util.Caffeine.JvmCaffeineParent;
import com.seek.friend.util.Redis.RedisUtil;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AiFriendCaffeine extends JvmCaffeineParent<Long, AiFriendDTO> {
    @Autowired
    public AiFriendCaffeine(AiFriendCaffeineConfig aiFriendCaffeineConfig, RedisUtil redisUtil , AiFriendRedisKeyConfig aiFriendRedisKeyConfig) {
        super(redisUtil, AiFriendDTO.class ,aiFriendRedisKeyConfig.getAiFriendInfoCaffeine());
        defaultInit(aiFriendCaffeineConfig.getAiFriend());
    }

    // 容器销毁清理缓存
    @PreDestroy
    public void destroy() {
        defaultDestroy();
    }
}
