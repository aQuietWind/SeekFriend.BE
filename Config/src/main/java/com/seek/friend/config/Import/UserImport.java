package com.seek.friend.config.Import;

import com.seek.friend.config.AutoConfig.UserAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(UserAutoConfig.class)
public @interface UserImport {}
