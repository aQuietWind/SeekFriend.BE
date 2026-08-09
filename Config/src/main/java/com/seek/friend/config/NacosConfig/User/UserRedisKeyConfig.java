package com.seek.friend.config.NacosConfig.User;

import com.seek.friend.config.Enum.ConfigKeyEnum;
import com.seek.friend.configobject.RedisData.RedisKeyData;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.User_Redis_Key_Config)
@Data
public class UserRedisKeyConfig {
    private RedisKeyData registerOpt;
    private RedisKeyData loginOpt;
    private RedisKeyData deleteUserOpt;
    private RedisKeyData updatePasswordOpt;
    private RedisKeyData registerCooldown;
    private RedisKeyData loginPasswordCooldown;
    private RedisKeyData loginRefreshCooldown;
    private RedisKeyData updateHeaderImageCooldown;
    private RedisKeyData updateInfoCooldown;
    private RedisKeyData updatePasswordCooldown;
    private RedisKeyData caffeineInfo;
    private RedisKeyData caffeinePhone;
    private RedisKeyData userIdCount;
}
