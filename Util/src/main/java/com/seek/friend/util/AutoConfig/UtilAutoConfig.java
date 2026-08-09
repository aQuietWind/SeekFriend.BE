package com.seek.friend.util.AutoConfig;

import com.seek.friend.util.CommonUtil.IdUtil;
import com.seek.friend.util.JWT.TokenUtil;
import com.seek.friend.util.OPT.OPTUtil;
import com.seek.friend.util.Redis.RedisBitMapUtil;
import com.seek.friend.util.Redis.RedisStreamUtil;
import com.seek.friend.util.Redis.RedisUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class UtilAutoConfig {
    @Bean
    @Lazy
    public RedisBitMapUtil redisBitMapUtil(StringRedisTemplate stringRedisTemplate) {
        return new RedisBitMapUtil(stringRedisTemplate);
    }
    @Bean
    @Lazy
    public RedisUtil redisUtil(StringRedisTemplate stringRedisTemplate) {
        return new RedisUtil(stringRedisTemplate);
    }
    @Bean
    @Lazy
    public RedisStreamUtil redisStreamUtil(StringRedisTemplate stringRedisTemplate) {
        return new RedisStreamUtil(stringRedisTemplate);
    }
    @Bean
    @Lazy
    public OPTUtil optUtil(RedisUtil redisUtil) {
        return new OPTUtil(redisUtil);
    }
    @Bean
    @Lazy
    public TokenUtil tokenUtil(RedisUtil redisUtil,StringRedisTemplate stringRedisTemplate) {
        return new TokenUtil(redisUtil,stringRedisTemplate);
    }
    @Bean
    @Lazy
    public IdUtil idUtil(RedisUtil redisUtil) {
        return new IdUtil(redisUtil);
    }
}
