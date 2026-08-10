package com.seek.friend.config.Enum;

public class ConfigKeyEnum {
    public static final String Common_Redis_Key="common.redis.key.name";
    public static final String Common_Param_Rules_Key="common.param.rules";
    public static final String JWT_Config="common.jwt";

    public static final String Gateway_Block_Config="gateway.self.block";
    public static final String Gateway_Redis_Key_Config="gateway.self.redis.key.name";
    public static final String Gateway_Request_Path_Config="gateway.self.request.path";
    public static final String Gateway_Caffeine_Config="gateway.self.jvm-caffeine";

    public static final String User_Params_Rules_Config="user.self.params.rules";
    public static final String User_Redis_Key_Config="user.self.redis.key";
    public static final String User_Caffeine_Config="user.self.jvm-caffeine";

    public static final String User_Friend_Params_Rules_Config="userfriend.self.params.rules";
    public static final String User_Friend_Redis_Key_Config="userfriend.self.redis.key";

    public static final String User_Topic_Config="mq.name.bind.user-topic";
    public static final String User_Friend_Topic_Config="mq.name.bind.user-topic";
}
