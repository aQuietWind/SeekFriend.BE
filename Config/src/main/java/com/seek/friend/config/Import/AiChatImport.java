package com.seek.friend.config.Import;

import com.seek.friend.config.AutoConfig.AiChatAutoConfig;
import com.seek.friend.config.AutoConfig.AiFriendAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AiChatAutoConfig.class)
public @interface AiChatImport {}
