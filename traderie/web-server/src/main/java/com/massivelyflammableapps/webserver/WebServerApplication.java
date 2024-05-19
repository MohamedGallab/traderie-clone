package com.massivelyflammableapps.webserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableCaching
public class WebServerApplication {
    @Value("${spring.rabbitmq.host}")
    private String applicationName;

    @Value("${spring.cassandra.contact-points}")
    private String cassandraContactPoints;
    
    public static void main(String[] args) {
        SpringApplication.run(WebServerApplication.class, args);
    }

    @PostConstruct
    public void printContactPoints() {
        // Print the values
        System.out.println("Application Name: " + applicationName);
        System.out.println("Cassandra Contact Points: " + cassandraContactPoints);
    }
}
