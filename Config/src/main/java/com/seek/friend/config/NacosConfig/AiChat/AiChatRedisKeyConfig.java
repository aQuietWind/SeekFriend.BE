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
    private RedisKeyData initAiFriendCooldown;
    private RedisKeyData updateAiFriendTextCooldown;
    private RedisKeyData updateAiFriendHeaderCooldown;
    private RedisKeyData completeAiFriendCooldown;
    private RedisKeyData aiFriendSimpleGetListCooldown;
    private RedisKeyData aiFriendDeleteCooldown;
    private RedisKeyData aiFriendIdCount;
    private RedisKeyData aiFriendInfoCaffeine;
}
