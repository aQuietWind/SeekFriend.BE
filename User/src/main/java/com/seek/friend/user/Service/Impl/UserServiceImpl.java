package com.seek.friend.user.Service.Impl;

import com.seek.friend.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.friend.config.NacosConfig.Common.CommonRedisKeyConfig;
import com.seek.friend.config.NacosConfig.RocketMQBindConfig.UserTopic;
import com.seek.friend.config.NacosConfig.User.UserParamsRulesConfig;
import com.seek.friend.config.NacosConfig.User.UserRedisKeyConfig;
import com.seek.friend.mqutil.RocketMQ.RocketMQUtil;
import com.seek.friend.serviceobject.User.UserDTO;
import com.seek.friend.user.Caffeine.PhoneCaffeine;
import com.seek.friend.user.Caffeine.UserCaffeine;
import com.seek.friend.user.Mapper.UserMapper;
import com.seek.friend.user.Service.UserService;
import com.seek.friend.util.Context.TokenIdContext;
import com.seek.friend.util.Exception.BizException;
import com.seek.friend.util.Exception.ErrorCodeEnum;
import com.seek.friend.util.FileUtil.FileSave;
import com.seek.friend.util.OPT.OPTUtil;
import com.seek.friend.util.Redis.RedisUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RefreshScope
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserCaffeine userCaffeine;
    private final UserParamsRulesConfig userParamsRulesConfig;
    private final PhoneCaffeine phoneCaffeine;
    private final CommonRedisKeyConfig commonRedisKeyConfig;
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final UserRedisKeyConfig userRedisKeyConfig;
    private final RedisUtil redisUtil;
    private final OPTUtil oPTUtil;
    private final RocketMQUtil rocketMQUtil;
    private final UserTopic userTopic;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserCaffeine userCaffeine
            , UserParamsRulesConfig userParamsRulesConfig, PhoneCaffeine phoneCaffeine, CommonRedisKeyConfig commonRedisKeyConfig, CommonParamRulesConfig commonParamRulesConfig
    , UserRedisKeyConfig userRedisKeyConfig, RedisUtil redisUtil, OPTUtil oPTUtil, RocketMQUtil rocketMQUtil, UserTopic userTopic) {
        this.userMapper = userMapper;
        this.userCaffeine = userCaffeine;
        this.userParamsRulesConfig = userParamsRulesConfig;
        this.phoneCaffeine = phoneCaffeine;
        this.commonRedisKeyConfig = commonRedisKeyConfig;
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.userRedisKeyConfig = userRedisKeyConfig;
        this.redisUtil = redisUtil;
        this.oPTUtil = oPTUtil;
        this.rocketMQUtil = rocketMQUtil;
        this.userTopic = userTopic;
    }

    //Bean注入完成后再执行初始化
    @PostConstruct
    public void initPath() {
        //提前创建目录，后面头像保存无需再校验
        FileSave.createDestDir(userParamsRulesConfig.getHeaderImageDest());
    }


    //获取某一用户详细信息
    @Override
    public UserDTO getUserDetailInfo(long userId){
        //验证id是否属于userId而不是别的
        commonParamRulesConfig.userIdCheck(userId);
        //从缓存中取出结果并且返回
        return userCaffeine.getAndAutoLoad(userId,userRedisKeyConfig.getCaffeineInfo(), key->userMapper.getUserDetailInfo(userId));
    }


    //获取用户个人信息
    @Override
    public  UserDTO getUserSelfInfo(){
        long userId= TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getIdCapacity());
        //直接返回mysql最新数据,避免用户自身的一致性问题
        return getUserDetailInfo(userId);
    }


    //获取更改密码所需的验证码
    @Override
    public String updateUserPasswordGetOpt(String phoneNumber){
        //验证格式
        commonParamRulesConfig.phoneNumberCheck(phoneNumber);
        //获取验证码
        return oPTUtil.generateAndRecordRedis(userRedisKeyConfig.getUpdatePasswordOpt(),phoneNumber,6);
    }


    //更改密码
    @Override
    public void updateUserPassword(String phoneNumber, String newPassword,String opt){
        //验证格式
        commonParamRulesConfig.phoneNumberCheck(phoneNumber);
        commonParamRulesConfig.passwordCheck(newPassword);
        //校验冷却期
        redisUtil.checkCooldown(userRedisKeyConfig.getUpdatePasswordCooldown(),phoneNumber);
        //检查验证码
        oPTUtil.checkFromRedis(userRedisKeyConfig.getUpdatePasswordOpt(),phoneNumber,opt);
        //如果mysql无目标数据会返回false
        if (!userMapper.updateUserPassword(phoneNumber,newPassword))throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
    }


    //更改头像
    @Override
    public void updateUserHeader(MultipartFile file){
        //获取id
        long userId=TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getIdCapacity());
        //冷却期校验
        redisUtil.checkCooldown(userRedisKeyConfig.getUpdateHeaderImageCooldown(),userId);
        //获取旧头像路径
        String oldAddr=userMapper.getHeaderPath(userId);
        //快速保存
        String addr=FileSave.quickCheckAndSaveFile(file,userParamsRulesConfig.getHeaderImageDest(), commonParamRulesConfig.getImageSize(),commonParamRulesConfig.getImageType());
        //检查是否成功
        if (!userMapper.updateUserHeader(userId,addr,oldAddr)) {
            //发消息使已经保存文件删除
            quickDeleteHeaderImage(addr);
            userCaffeine.deleteAllCaffeine(userId,userRedisKeyConfig.getCaffeineInfo());
            throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
        }
        //发送消息到mq中删除旧文件
        if (oldAddr!=null&&!oldAddr.isBlank()) quickDeleteHeaderImage(oldAddr);
    }


    //更改用户自身信息
    @Override
    public void updateUserInfo(UserDTO userDTO){
        //获取Id并检验
        long userId=TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getIdCapacity());
        //检验性别，姓名，生日时期（出生日期）
        commonParamRulesConfig.sexCheck(userDTO.getSex());
        commonParamRulesConfig.birthdayCheck(userDTO.getBirthday());
        userParamsRulesConfig.usernameCheck(userDTO.getUsername());
        //检查冷却
        redisUtil.checkCooldown(userRedisKeyConfig.getUpdateInfoCooldown(),userId);
        userDTO.setUserId(userId);
        //更新信息
        quickUpdateUser(k->userMapper.updateUserInfo(userDTO),userId);
    }


    //多用户粗览信息获取
    @Override
    public List<UserDTO> getUsersSimpleInfo(List<Long> userIds){
        if (userIds.isEmpty())return new ArrayList<>();
        //校验参数
        for (Long userId:userIds) commonParamRulesConfig.userIdCheck(userId);
        return userMapper.getUsersSimpleInfo(userIds);
    }


    //删除所需获取验证码
    @Override
    public String getUserDeleteOpt(){
        //获取userId并且校验
        long userId=TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getIdCapacity());
        //获取手机号,只做业务的手机号获取模拟，无实际用途
        String phoneNumber=phoneCaffeine.getAndAutoLoad(userId,userRedisKeyConfig.getCaffeinePhone(),userMapper::getPhoneNumber);
        //产生验证码
        return oPTUtil.generateAndRecordRedis(userRedisKeyConfig.getDeleteUserOpt(),phoneNumber,6);
    }


    //删除用户
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(String opt){
        //userId获取
        long userId=TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getIdCapacity());
        //检验验证码
        oPTUtil.checkFromRedis(userRedisKeyConfig.getDeleteUserOpt(),phoneCaffeine.getAndAutoLoad(userId,userRedisKeyConfig.getCaffeinePhone(),userMapper::getPhoneNumber),opt);
        //逻辑删除用户
        quickUpdateUser(k->userMapper.deleteUser(userId),userId);
        //清空token
        redisUtil.delete(commonRedisKeyConfig.getTokenStore(),userId);
        //删除旧照片
        quickDeleteHeaderImage(userMapper.getDeleteHeaderPath(userId));
    }


    private void quickUpdateUser(Function<Long,Boolean> function,long userId){
        userCaffeine.updateAndRemoveCaffeine(userId,userRedisKeyConfig.getCaffeineInfo(),function);
    }

    private void quickDeleteHeaderImage(String addr){
        rocketMQUtil.send(userTopic.getTopicName(),userTopic.getDeleteFile().getTag(),Paths.get(userParamsRulesConfig.getHeaderImageDest(),addr).toString());
    }
















}
