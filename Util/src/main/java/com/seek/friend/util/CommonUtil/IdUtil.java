package com.seek.friend.util.CommonUtil;


import com.seek.friend.configobject.RedisData.RedisKeyData;
import com.seek.friend.util.Redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@Lazy
public class IdUtil {
    private static final Random random=new Random();
    private final RedisUtil redisUtil;
    @Autowired
    public IdUtil(RedisUtil redisUtil) {
        this.redisUtil=redisUtil;
    }

    public long IdGenerateByIncreaseRandom(RedisKeyData key, int randomNumberMax) {
        return redisUtil.increase(key,null,random.nextInt(randomNumberMax));
    }
    public long IdGenerateByIncrease(RedisKeyData key) {
        return redisUtil.increase(key,null,1);
    }
}














