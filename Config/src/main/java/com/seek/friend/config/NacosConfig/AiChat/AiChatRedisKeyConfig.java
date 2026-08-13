package com.seek.friend.config.NacosConfig.AiChat;

import com.seek.friend.config.Enum.ConfigKeyEnum;
import com.seek.friend.configobject.RedisData.RedisKeyData;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Ai_Chat_Redis_Key_Config)
@Data
public class AiChatRedisKeyConfig {
    private RedisKeyData roomGetListCooldown;
    private RedisKeyData recordGetListCooldown;
    private RedisKeyData recordInsertCooldown;
    private RedisKeyData recordInitiativeCooldown;
    private RedisKeyData roomIdCount;
    private RedisKeyData recordIdCount;
    private RedisKeyData aiFriendInfoCaffeine;
}
