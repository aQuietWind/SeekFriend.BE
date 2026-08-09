package com.seek.friend.config.Import;

import com.seek.friend.config.AutoConfig.GatewayAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(GatewayAutoConfig.class)
public @interface GatewayImport {}
