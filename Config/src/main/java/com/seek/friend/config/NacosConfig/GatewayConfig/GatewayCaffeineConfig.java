package com.seek.friend.config.NacosConfig.GatewayConfig;

import com.seek.friend.config.Enum.ConfigKeyEnum;
import com.seek.friend.configobject.CaffeineData.CaffeineData;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Data
@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Gateway_Caffeine_Config)
public class GatewayCaffeineConfig {
    private CaffeineData ipBlock;
    private CaffeineData idBlock;
}
