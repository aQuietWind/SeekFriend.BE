package com.seek.friend.config.Import;

import com.seek.friend.config.AutoConfig.RocketMQBindAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RocketMQBindAutoConfig.class)
public @interface RocketMQBindImport {}
