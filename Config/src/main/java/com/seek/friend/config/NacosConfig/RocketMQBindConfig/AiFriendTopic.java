package com.seek.friend.config.NacosConfig.RocketMQBindConfig;


import com.seek.friend.config.Enum.ConfigKeyEnum;
import com.seek.friend.configobject.RocketMQData.ConsumerData;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Data
@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Ai_Friend_Topic_Config)
public class AiFriendTopic {
    private String topicName;
    private ConsumerData changeAiFriendAmount;
    private ConsumerData deleteChatRoom;
    private ConsumerData initChatRoom;
    private ConsumerData deleteFile;
}
