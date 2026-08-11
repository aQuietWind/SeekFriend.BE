package com.seek.friend.config.NacosConfig.UserChat;

import com.seek.friend.config.Enum.ConfigKeyEnum;
import com.seek.friend.util.Exception.BizException;
import com.seek.friend.util.Exception.ErrorCodeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.User_Chat_Params_Rules_Config)
@Data
public class UserChatParamsRulesConfig {
    private Integer descriptionMax;
    private String chatRecordImageDest;
    private Long fileDeleteDelaySeconds;
    private Long recordAbleWithdrawSeconds;

    public void descriptionCheck(String description) {
        if (description==null|| description.isEmpty() ||description.length()>descriptionMax)throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    }
}
