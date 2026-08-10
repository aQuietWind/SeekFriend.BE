package com.seek.friend.config.NacosConfig.Common;


import com.seek.friend.config.Enum.ConfigKeyEnum;
import com.seek.friend.configobject.CommonData.JWTGlobalData;
import com.seek.friend.configobject.CommonData.JWTRoleData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.JWT_Config)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTConfig {
    private JWTRoleData user;
    private JWTGlobalData global;

    public JWTRoleData[] getAllJWTData() {
        return new JWTRoleData[]{user};
    }
}
