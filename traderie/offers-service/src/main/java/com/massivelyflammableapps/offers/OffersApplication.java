package com.massivelyflammableapps.offers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;
import java.util.*;

@SpringBootApplication
public class OffersApplication {
    private final static Logger log = LoggerFactory.getLogger(OffersApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OffersApplication.class, args);
    }

    // @Bean
    // public CommandLineRunner clr(VetRepository vetRepository) {

    //     return args -> {
    //         vetRepository.deleteAll();

    //         Vet john = new Vet(UUID.randomUUID(), "John", "Doe", new HashSet<>(Arrays.asList("surgery")));
    //         Vet jane = new Vet(UUID.randomUUID(), "Jane", "Doe", new HashSet<>(Arrays.asList("radiology, surgery")));

    //         Vet savedJohn = vetRepository.save(john);
    //         Vet savedJane = vetRepository.save(jane);

    //         vetRepository.findAll()
    //                 .forEach(v -> log.info("Vet: {}", v.getFirstName()));

    //         vetRepository.findById(savedJohn.getId())
    //                 .ifPresent(v -> log.info("Vet by id: {}", v.getFirstName()));
    //     };

    //     // return args -> {
    //     // offerRepository.deleteAll();


    //     //     Set<Set<OfferedProduct>> offeredProducts1 = new HashSet<>();

    //     //     Set<OfferedProduct> offeredProducts1_1 = new HashSet<>(Arrays.asList(
    //     //         new OfferedProduct(2, "1",UUID.randomUUID().toString(), "Product 1", "icon1.png"),
    //     //         new OfferedProduct(3, "2", UUID.randomUUID().toString(),"Product 2", "icon2.png")
    //     //     ));

    //     //     Set<OfferedProduct> offeredProducts1_2 = new HashSet<>(Arrays.asList(
    //     //         new OfferedProduct(4, "1",UUID.randomUUID().toString(), "Product 1", "icon1.png"),
    //     //         new OfferedProduct(5, "2", UUID.randomUUID().toString(),"Product 2", "icon2.png")
    //     //     ));

    //     //     offeredProducts1.add(offeredProducts1_1);
    //     //     offeredProducts1.add(offeredProducts1_2);
                
    //     //     Offer offer1 = new Offer(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),
    //     //         new Timestamp(0), "accepted",offeredProducts1);
                
                           
    //     //     Offer savedOffer1 = offerRepository.save(offer1);

    //     //     offerRepository.findAll().forEach(offer -> log.info("Offer: {}", offer.getId()));

    //     //     offerRepository.findById(savedOffer1.getId())
    //     //             .ifPresent(offer -> log.info("Offer by id: {}", offer.getId()));
    //     // };
    // }
}

