package com.seek.friend.config.NacosConfig.UserChat;

import com.seek.friend.config.Enum.ConfigKeyEnum;
import com.seek.friend.configobject.RedisData.RedisKeyData;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.User_Friend_Redis_Key_Config)
@Data
public class UserChatRedisKeyConfig {
    private RedisKeyData roomGetListCooldown;
    private RedisKeyData recordGetListCooldown;
    private RedisKeyData recordInsertCooldown;
    private RedisKeyData recordWithdrawCooldown;
    private RedisKeyData roomIdCount;
    private RedisKeyData recordIdCount;
}
