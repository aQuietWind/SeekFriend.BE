package com.seek.friend.config.NacosConfig.GatewayConfig;

import com.seek.friend.config.Enum.ConfigKeyEnum;
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
    private String idCheck;
    private String ipCheck;
    private String idBlock;
    private String ipBlock;
}
