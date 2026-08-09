package com.seek.friend.user.Caffeine;

import com.seek.friend.config.NacosConfig.User.UserCaffeineConfig;
import com.seek.friend.util.Caffeine.JvmCaffeineParent;
import com.seek.friend.util.Redis.RedisUtil;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PhoneCaffeine extends JvmCaffeineParent<Long,String> {
    @Autowired
    public PhoneCaffeine(UserCaffeineConfig userCaffeineConfig, RedisUtil redisUtil) {
        super(redisUtil, String.class);
        defaultInit(userCaffeineConfig.getPhone());
    }

    // 容器销毁清理缓存
    @PreDestroy
    public void destroy() {
        defaultDestroy();
    }
}
