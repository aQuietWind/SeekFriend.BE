package com.seek.friend.config.NacosConfig.AiChat;

import com.seek.friend.config.Enum.ConfigKeyEnum;
import com.seek.friend.util.Exception.BizException;
import com.seek.friend.util.Exception.ErrorCodeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Ai_Chat_Params_Rules_Config)
@Data
public class AiChatParamsRulesConfig {
    private Integer recordDescriptionMax;
    private String recordFileDest;

    public void recordDescriptionCheck(String description){
        if (description!=null && description.length()>recordDescriptionMax)throwParamError();
    }
    private void throwParamError(){
        throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    }
}
