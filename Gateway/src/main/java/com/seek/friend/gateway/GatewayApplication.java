package com.seek.friend.gateway;

import com.seek.friend.config.Import.CommonImport;
import com.seek.friend.config.Import.GatewayImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@CommonImport
@GatewayImport
public class GatewayApplication {
    public static void main(String[] args) {
    SpringApplication.run(GatewayApplication.class, args);
}
}
