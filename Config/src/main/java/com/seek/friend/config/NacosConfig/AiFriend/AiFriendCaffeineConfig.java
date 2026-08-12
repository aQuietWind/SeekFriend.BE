package com.seek.friend.config.NacosConfig.AiFriend;

import com.seek.friend.config.Enum.ConfigKeyEnum;
import com.seek.friend.configobject.CaffeineData.CaffeineData;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.User_Caffeine_Config)
@Data
public class AiFriendCaffeineConfig {
    private CaffeineData aiFriend;
}
