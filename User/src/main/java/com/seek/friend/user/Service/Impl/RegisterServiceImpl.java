package com.seek.friend.user.Service.Impl;

import com.seek.friend.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.friend.config.NacosConfig.User.UserRedisKeyConfig;
import com.seek.friend.user.Mapper.RegisterMapper;
import com.seek.friend.user.Service.RegisterService;
import com.seek.friend.util.CommonUtil.IdUtil;
import com.seek.friend.util.OPT.OPTUtil;
import com.seek.friend.util.Redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;


@Service
@RefreshScope
@Slf4j
public class RegisterServiceImpl implements RegisterService {
    private final RegisterMapper registerMapper;
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final UserRedisKeyConfig userRedisKeyConfig;
    private final OPTUtil oPTUtil;
    private final RedisUtil redisUtil;
    private final IdUtil idUtil;

    @Autowired
    public RegisterServiceImpl(RegisterMapper registerMapper, CommonParamRulesConfig commonParamRulesConfig
            , UserRedisKeyConfig userRedisKeyConfig, OPTUtil oPTUtil, RedisUtil redisUtil, IdUtil idUtil) {
        this.registerMapper = registerMapper;
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.userRedisKeyConfig = userRedisKeyConfig;
        //初始化userIdCount
        redisUtil.trySetString(userRedisKeyConfig.getUserIdCount(),null,""+commonParamRulesConfig.getIdCapacity());
        this.oPTUtil = oPTUtil;
        this.redisUtil = redisUtil;
        this.idUtil = idUtil;
    }


    //获取注册所需的验证码
    @Override
    public String registerGetOpt(String phoneNumber) {
        log.info("phone number:{} ,进行注册获取验证码",phoneNumber);
        //验证手机号
        commonParamRulesConfig.phoneNumberCheck(phoneNumber);
        return oPTUtil.generateAndRecordRedis(userRedisKeyConfig.getRegisterOpt(),phoneNumber,6);
    }

    //注册用户
    @Override
    public void registerUser(String phoneNumber, String password, String opt) {
        //检验格式
        commonParamRulesConfig.phoneNumberCheck(phoneNumber);
        commonParamRulesConfig.passwordCheck(password);
        //检验验证码
        oPTUtil.checkFromRedis(userRedisKeyConfig.getRegisterOpt(), phoneNumber,opt);
        //校验冷却期
        redisUtil.checkCooldown(userRedisKeyConfig.getRegisterCooldown(),phoneNumber);
        //生成id
        long userId= idUtil.IdGenerateByIncrease(userRedisKeyConfig.getUserIdCount());
        //写入mysql,失败会报错
        registerMapper.insertUser(userId, phoneNumber, password);
        log.info("phone number:{} ,成功注册用户",phoneNumber);
    }








}
