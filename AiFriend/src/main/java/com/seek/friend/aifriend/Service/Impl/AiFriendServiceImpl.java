package com.seek.friend.aifriend.Service.Impl;

import com.seek.friend.aifriend.Service.AiFriendService;
import com.seek.friend.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@RefreshScope
@Slf4j
@Service
public class AiFriendServiceImpl implements AiFriendService {

    private final CommonParamRulesConfig commonParamRulesConfig;
    @Autowired
    public AiFriendServiceImpl(CommonParamRulesConfig commonParamRulesConfig) {
        this.commonParamRulesConfig = commonParamRulesConfig;
    }

    @Override
    public void init(AiFriendDTO aiFriend){

    }
}
