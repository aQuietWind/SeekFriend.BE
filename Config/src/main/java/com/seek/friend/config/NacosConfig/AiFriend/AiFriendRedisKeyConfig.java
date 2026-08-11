package com.seek.friend.config.NacosConfig.AiFriend;

import com.seek.friend.config.Enum.ConfigKeyEnum;
import com.seek.friend.configobject.RedisData.RedisKeyData;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.User_Friend_Redis_Key_Config)
@Data
public class AiFriendRedisKeyConfig {
    private RedisKeyData applyConnectionCooldown;
    private RedisKeyData getApplicantListCooldown;
    private RedisKeyData getRespondentListCooldown;
    private RedisKeyData getFriendListCooldown;
    private RedisKeyData respondApplicationCooldown;
    private RedisKeyData deleteConnectionCooldown;
    private RedisKeyData connectionIdCount;
}
