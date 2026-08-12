package com.seek.friend.aifriend.Service.Impl;

import com.seek.friend.aifriend.Mapper.AiFriendMapper;
import com.seek.friend.aifriend.Service.AiFriendService;
import com.seek.friend.config.NacosConfig.AiFriend.AiFriendParamsRulesConfig;
import com.seek.friend.config.NacosConfig.AiFriend.AiFriendRedisKeyConfig;
import com.seek.friend.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.friend.configobject.RedisData.RedisKeyData;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;
import com.seek.friend.util.CommonUtil.IdUtil;
import com.seek.friend.util.Context.TokenIdContext;
import com.seek.friend.util.Redis.RedisUtil;
import dev.langchain4j.model.chat.ChatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@RefreshScope
@Slf4j
@Service
public class AiFriendServiceImpl implements AiFriendService {

    private final CommonParamRulesConfig commonParamRulesConfig;
    private final AiFriendParamsRulesConfig aiFriendParamsRulesConfig;
    private final AiFriendRedisKeyConfig aiFriendRedisKeyConfig;
    private final ChatModel chatModel;
    private final RedisUtil redisUtil;
    private final IdUtil idUtil;
    private final AiFriendMapper aiFriendMapper;
    @Autowired
    public AiFriendServiceImpl(CommonParamRulesConfig commonParamRulesConfig,AiFriendParamsRulesConfig aiFriendParamsRulesConfig
            ,AiFriendRedisKeyConfig aiFriendRedisKeyConfig, ChatModel chatModel,RedisUtil redisUtil,IdUtil idUtil,AiFriendMapper aiFriendMapper) {
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.aiFriendParamsRulesConfig = aiFriendParamsRulesConfig;
        this.aiFriendRedisKeyConfig = aiFriendRedisKeyConfig;
        this.chatModel = chatModel;
        this.redisUtil = redisUtil;
        this.idUtil = idUtil;
        this.aiFriendMapper = aiFriendMapper;
    }

    @Override
    public long initText(AiFriendDTO aiFriend){
        //检查参数
        aiFriendParamsRulesConfig.nameCheck(aiFriend.getName());
        aiFriendParamsRulesConfig.descriptionCheck(aiFriend.getDescription());
        aiFriendParamsRulesConfig.hobbyCheck(aiFriend.getHobbies());
        aiFriendParamsRulesConfig.characteristicCheck(aiFriend.getCharacteristic());
        aiFriendParamsRulesConfig.likeScoreCheck(aiFriend.getLikeScore());
        //检查冷却并获取Id
        long userId=quickCheckCooldownAndGetUserId(aiFriendRedisKeyConfig.getInitAiFriendCooldown());
        //设置必要的参数
        aiFriend.setUserId(userId);
        aiFriend.setAiFriendId(idUtil.IdGenerateByIncrease(aiFriendRedisKeyConfig.getAiFriendIdCount()));
        //插入DB
        aiFriendMapper.initText(aiFriend);
        //返回id
        return aiFriend.getAiFriendId();
    }




    private long quickCheckCooldownAndGetUserId(RedisKeyData key){
        long userId= TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getIdCapacity());
        redisUtil.checkCooldown(key,userId);
        return userId;
    }













}
