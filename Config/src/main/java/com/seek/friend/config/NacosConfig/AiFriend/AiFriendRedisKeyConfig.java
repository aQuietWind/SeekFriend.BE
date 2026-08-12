package com.seek.friend.config.NacosConfig.AiFriend;

import com.seek.friend.config.Enum.ConfigKeyEnum;
import com.seek.friend.configobject.RedisData.RedisKeyData;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Ai_Friend_Redis_Key_Config)
@Data
public class AiFriendRedisKeyConfig {
    private RedisKeyData initAiFriendTextCooldown;
    private RedisKeyData initAiFriendHeaderCooldown;
    private RedisKeyData aiFriendGetListCooldown;
    private RedisKeyData aiFriendDeleteCooldown;
    private RedisKeyData aiFriendIdCount;
    private RedisKeyData aiFriendInfoCaffeine;
}
