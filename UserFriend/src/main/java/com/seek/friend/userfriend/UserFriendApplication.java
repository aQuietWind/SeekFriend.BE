package com.seek.friend.userfriend;

import com.seek.friend.config.Import.CommonImport;
import com.seek.friend.config.Import.RocketMQBindImport;
import com.seek.friend.config.Import.UserFriendImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@CommonImport
@UserFriendImport
@RocketMQBindImport
public class UserFriendApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserFriendApplication.class, args);
    }

}
