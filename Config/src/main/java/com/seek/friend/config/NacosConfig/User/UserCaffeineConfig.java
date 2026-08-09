package com.seek.friend.config.NacosConfig.User;

import com.seek.friend.config.Enum.ConfigKeyEnum;
import com.seek.friend.configobject.CaffeineData.CaffeineData;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.User_Caffeine_Config)
@Data
public class UserCaffeineConfig {
    private CaffeineData user;
    private CaffeineData phone;
}
