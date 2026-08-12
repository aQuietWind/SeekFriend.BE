package com.seek.friend.config.NacosConfig.AiFriend;

import com.seek.friend.config.Enum.ConfigKeyEnum;
import com.seek.friend.util.Exception.BizException;
import com.seek.friend.util.Exception.ErrorCodeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Ai_Friend_Params_Rules_Config)
@Data
public class AiFriendParamsRulesConfig {
    private Integer descriptionMax;
    private Integer hobbyMax;
    private Integer characteristicMax;
    private Integer encounterReasonMax;
    private Integer historyMax;
    private Integer nameMax;
    private Integer likeScoreMax;
    private String headerImageDest;

    public void descriptionCheck(String description){
        if (description!=null && description.length()>descriptionMax)throwParamError();
    }
    public void hobbyCheck(String hobby){
        if (hobby!=null && hobby.length()>hobbyMax)throwParamError();
    }
    public void characteristicCheck(String characteristic){
        if (characteristic!=null && characteristic.length()>characteristicMax)throwParamError();
    }
    public void encounterReasonCheck(String encounterReason){
        if (encounterReason!=null && encounterReason.length()>encounterReasonMax)throwParamError();
    }
    public void historyCheck(String history){
        if (history!=null && history.length()>historyMax)throwParamError();
    }
    public void nameCheck(String name){
        if (name!=null && name.length()>nameMax)throwParamError();
    }
    public void likeScoreCheck(Integer likeScore){
        if (likeScore!=null && likeScore>likeScoreMax)throwParamError();
    }

    private void throwParamError(){
        throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    }
}
