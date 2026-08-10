package com.seek.friend.userchat;

import com.seek.friend.config.Import.CommonImport;
import com.seek.friend.config.Import.RocketMQBindImport;
import com.seek.friend.config.Import.UserChatImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@CommonImport
@UserChatImport
@RocketMQBindImport
public class UserChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserChatApplication.class, args);
    }

}
