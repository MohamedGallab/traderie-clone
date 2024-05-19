package com.massivelyflammableapps.offers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OffersApplication {
    public static void main(String[] args) {
        System.out.println("hehe");
        SpringApplication.run(OffersApplication.class, args);
    }
}
