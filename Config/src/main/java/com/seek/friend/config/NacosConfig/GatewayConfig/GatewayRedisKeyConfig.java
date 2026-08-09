package com.seek.friend.config.NacosConfig.GatewayConfig;

import com.seek.friend.config.Enum.ConfigKeyEnum;
import com.seek.friend.configobject.RedisData.RedisKeyData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Gateway_Redis_Key_Config)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GatewayRedisKeyConfig {
    private RedisKeyData idCheck;
    private RedisKeyData ipCheck;
    private RedisKeyData idBlock;
    private RedisKeyData ipBlock;
}
