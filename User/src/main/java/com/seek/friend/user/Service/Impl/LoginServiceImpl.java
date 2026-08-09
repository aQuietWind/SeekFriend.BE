package com.seek.friend.user.Service.Impl;

import com.seek.friend.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.friend.config.NacosConfig.Common.CommonRedisKeyConfig;
import com.seek.friend.config.NacosConfig.Common.JWTConfig;
import com.seek.friend.config.NacosConfig.User.UserParamsRulesConfig;
import com.seek.friend.config.NacosConfig.User.UserRedisKeyConfig;
import com.seek.friend.configobject.CommonData.JWTRoleData;
import com.seek.friend.serviceobject.User.UserDTO;
import com.seek.friend.user.Mapper.LoginMapper;
import com.seek.friend.user.Service.LoginService;
import com.seek.friend.util.Context.TokenIdContext;
import com.seek.friend.util.Exception.BizException;
import com.seek.friend.util.Exception.ErrorCodeEnum;
import com.seek.friend.util.JWT.TokenUtil;
import com.seek.friend.util.OPT.OPTUtil;
import com.seek.friend.util.Redis.RedisUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
@Slf4j
public class LoginServiceImpl implements LoginService {
    private final JWTRoleData JWTUser;
    private final JWTConfig jwtConfig;
    private final CommonRedisKeyConfig commonRedisKeyConfig;
    private final LoginMapper loginMapper;
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final UserRedisKeyConfig userRedisKeyConfig;
    private final OPTUtil oPTUtil;
    private final RedisUtil redisUtil;
    private final TokenUtil tokenUtil;

    @Autowired
    public LoginServiceImpl(JWTConfig jwtConfig, LoginMapper loginMapper, UserParamsRulesConfig userParamsRulesConfig
    , CommonRedisKeyConfig commonRedisKeyConfig, UserRedisKeyConfig userRedisKeyConfig
    , CommonParamRulesConfig commonParamRulesConfig, OPTUtil oPTUtil, RedisUtil redisUtil, TokenUtil tokenUtil) {
        this.jwtConfig = jwtConfig;
        this.JWTUser = jwtConfig.getUser();
        this.commonRedisKeyConfig = commonRedisKeyConfig;
        this.loginMapper = loginMapper;
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.userRedisKeyConfig = userRedisKeyConfig;
        this.oPTUtil = oPTUtil;
        this.redisUtil = redisUtil;
        this.tokenUtil = tokenUtil;
    }

    @Override
    //获取登录所需验证码
    public String loginGetOpt(String phoneNumber){
        log.info("phone number:{} ,尝试获取登录验证码",phoneNumber);
        //验证手机号
        commonParamRulesConfig.phoneNumberCheck(phoneNumber);
        //生成token并且记录于redis
        return oPTUtil.generateAndRecordRedis(userRedisKeyConfig.getLoginOpt(), phoneNumber,6);
    }

    @Override
    //手机号与验证码登录
    public UserDTO login(String phoneNumber, String opt, HttpServletResponse response){
        //检验格式
        commonParamRulesConfig.phoneNumberCheck(phoneNumber);
        //检查验证码
        oPTUtil.checkFromRedis( userRedisKeyConfig.getLoginOpt(), phoneNumber, opt);
        //根据手机号获取目标
        return loginAndGetToken(loginMapper.getUserByPhoneNumber(phoneNumber),response);
    }

    //通过密码登录
    @Override
    public UserDTO loginByPassword(String phoneNumber, String password, HttpServletResponse response){
        //检验格式
        commonParamRulesConfig.phoneNumberCheck(phoneNumber);
        commonParamRulesConfig.passwordCheck(password);
        //检验冷却时间
        redisUtil.checkCooldown(userRedisKeyConfig.getLoginOpt(), phoneNumber);
        //验证登录
        return loginAndGetToken(loginMapper.getUserByPassword(phoneNumber, password),response);
    }

    //刷新token用
    @Override
    public void loginRefresh(HttpServletResponse response){
        long userId= TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getIdCapacity());
        //检查冷却期，防止频繁刷新token
        redisUtil.checkCooldown( userRedisKeyConfig.getLoginRefreshCooldown(),userId);
        loginAndGetToken(userId, response);
    }

    //发放登录信息
    private UserDTO loginAndGetToken(UserDTO user, HttpServletResponse response){
        if (user == null) throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
        //获取token，并且放在请求头上
        getTokenByUtil(user.getUserId(),  response);
        return user;
    }

    //同样为发放登录消息，不过只需要id
    private void loginAndGetToken(long userId, HttpServletResponse response){
        getTokenByUtil(userId,response);
    }

    //从TokenUtil中快速获取token
    private void getTokenByUtil(long userId, HttpServletResponse response){
        //获取token，并且放在请求头上
        tokenUtil.getAndRecordToken(userId,response,JWTUser,jwtConfig.getGlobal(),commonRedisKeyConfig.getTokenStore());
    }



















}
