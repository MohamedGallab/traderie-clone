package com.massivelyflammableapps.messages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MessagesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessagesApplication.class, args);
    }
}
