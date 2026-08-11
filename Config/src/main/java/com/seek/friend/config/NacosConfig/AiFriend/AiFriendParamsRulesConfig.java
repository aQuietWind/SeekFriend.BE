package com.seek.friend.config.NacosConfig.AiFriend;

import com.seek.friend.config.Enum.ConfigKeyEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.User_Friend_Params_Rules_Config)
@Data
public class AiFriendParamsRulesConfig {
}
