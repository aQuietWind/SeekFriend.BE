package com.seek.friend.config.Import;

import com.seek.friend.config.AutoConfig.AiFriendAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AiFriendAutoConfig.class)
public @interface AiFriendImport {}
