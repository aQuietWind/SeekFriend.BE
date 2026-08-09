package com.seek.friend.user;

import com.seek.friend.config.Import.CommonImport;
import com.seek.friend.config.Import.RocketMQBindImport;
import com.seek.friend.config.Import.UserImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@CommonImport
@UserImport
@RocketMQBindImport
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
