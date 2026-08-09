package com.seek.friend.config.NacosConfig.Common;

import com.seek.friend.config.Enum.ConfigKeyEnum;
import com.seek.friend.configobject.RedisData.RedisKeyData;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Common_Redis_Key)
@Data
public class CommonRedisKeyConfig {
    private RedisKeyData tokenStore;
}
