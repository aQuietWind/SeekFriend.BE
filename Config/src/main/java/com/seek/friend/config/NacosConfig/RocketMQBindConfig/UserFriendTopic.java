package com.seek.friend.config.NacosConfig.RocketMQBindConfig;


import com.seek.friend.config.Enum.ConfigKeyEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Data
@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.User_Topic_Config)
public class UserFriendTopic {
}
