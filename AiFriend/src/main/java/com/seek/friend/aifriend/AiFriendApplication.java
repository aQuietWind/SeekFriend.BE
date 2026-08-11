package com.seek.friend.aifriend;

import com.seek.friend.config.Import.AiFriendImport;
import com.seek.friend.config.Import.CommonImport;
import com.seek.friend.config.Import.RocketMQBindImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RocketMQBindImport
@AiFriendImport
@CommonImport
public class AiFriendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiFriendApplication.class, args);
    }

}
