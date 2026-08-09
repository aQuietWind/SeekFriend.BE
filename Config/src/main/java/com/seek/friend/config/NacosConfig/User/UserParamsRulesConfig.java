package com.seek.friend.config.NacosConfig.User;

import com.seek.friend.config.Enum.ConfigKeyEnum;
import com.seek.friend.util.Exception.BizException;
import com.seek.friend.util.Exception.ErrorCodeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.User_Params_Rules_Config)
@Data
public class UserParamsRulesConfig {
    private int usernameLengthMax;
    private String headerImageDest;

    //------------------------
    //校验参数
    public void usernameCheck(String username) {
        if (username!=null&&username.length()>usernameLengthMax) throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    };
}
