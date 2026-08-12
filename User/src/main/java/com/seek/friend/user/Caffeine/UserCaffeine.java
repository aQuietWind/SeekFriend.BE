package com.seek.friend.user.Caffeine;

import com.seek.friend.config.NacosConfig.User.UserCaffeineConfig;
import com.seek.friend.config.NacosConfig.User.UserRedisKeyConfig;
import com.seek.friend.serviceobject.User.UserDTO;
import com.seek.friend.util.Caffeine.JvmCaffeineParent;
import com.seek.friend.util.Redis.RedisUtil;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserCaffeine extends JvmCaffeineParent<Long, UserDTO> {
    @Autowired
    public UserCaffeine(UserCaffeineConfig userCaffeineConfig, RedisUtil redisUtil , UserRedisKeyConfig userRedisKeyConfig) {
        super(redisUtil, UserDTO.class , userRedisKeyConfig.getCaffeineInfo());
        defaultInit(userCaffeineConfig.getUser());
    }

    // 容器销毁清理缓存
    @PreDestroy
    public void destroy() {
        defaultDestroy();
    }
}
