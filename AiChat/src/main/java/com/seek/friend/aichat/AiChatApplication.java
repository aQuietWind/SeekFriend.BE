package com.seek.friend.aichat;

import com.seek.friend.config.Import.AiChatImport;
import com.seek.friend.config.Import.CommonImport;
import com.seek.friend.config.Import.RocketMQBindImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AiChatImport
@CommonImport
@RocketMQBindImport
public class AiChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiChatApplication.class, args);
    }

}
