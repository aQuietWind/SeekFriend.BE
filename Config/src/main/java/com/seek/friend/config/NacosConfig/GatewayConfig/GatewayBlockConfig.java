package com.seek.friend.config.NacosConfig.GatewayConfig;

import com.seek.friend.config.Enum.ConfigKeyEnum;
import com.seek.friend.configobject.CommonData.CountsBlockData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Gateway_Block_Config)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatewayBlockConfig {
    private CountsBlockData ip;
    private CountsBlockData id;
}
