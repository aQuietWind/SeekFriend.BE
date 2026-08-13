package com.seek.friend.aichat.Caffeine;

import com.seek.friend.config.NacosConfig.AiChat.AiChatCaffeineConfig;
import com.seek.friend.config.NacosConfig.AiChat.AiChatRedisKeyConfig;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;
import com.seek.friend.util.Caffeine.JvmCaffeineParent;
import com.seek.friend.util.Redis.RedisUtil;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AiFriendCaffeine extends JvmCaffeineParent<Long, AiFriendDTO> {
    @Autowired
    public AiFriendCaffeine(AiChatCaffeineConfig aiChatCaffeineConfig, RedisUtil redisUtil , AiChatRedisKeyConfig aiChatRedisKeyConfig) {
        super(redisUtil, AiFriendDTO.class ,aiChatRedisKeyConfig.getAiFriendInfoCaffeine());
        defaultInit(aiChatCaffeineConfig.getAiFriend());
    }

    // 容器销毁清理缓存
    @PreDestroy
    public void destroy() {
        defaultDestroy();
    }
}
