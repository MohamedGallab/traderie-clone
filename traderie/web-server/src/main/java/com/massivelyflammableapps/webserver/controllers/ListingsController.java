package com.massivelyflammableapps.webserver.controllers;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.massivelyflammableapps.shared.dto.listings.GetListingsByGameByProductDTO;
import com.massivelyflammableapps.shared.dto.listings.GetListingsByGameByUserDTO;
import com.massivelyflammableapps.shared.dto.listings.GetMyListingsByGameDTO;
import com.massivelyflammableapps.shared.dto.listings.ListingDTO;
import com.massivelyflammableapps.shared.dto.listings.ListingUpdateDTO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/listings")
@Slf4j
public class ListingsController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${listings-service.queue.name}")
    private String listingsQueueName;

    @GetMapping
    public ResponseEntity<List<ListingDTO>> getAllListingsByGameByProduct(
            @RequestBody GetListingsByGameByProductDTO request) {
        try {
            List<ListingDTO> listings = rabbitTemplate.convertSendAndReceiveAsType("", listingsQueueName, request,
                    new ParameterizedTypeReference<List<ListingDTO>>() {
                    });
            log.info("getAllListingsByGameByProduct executed successfully.");
            return ResponseEntity.ok(listings);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/userListings")
    public ResponseEntity<List<ListingDTO>> getAllListingsByGameByUser(
            @RequestBody GetListingsByGameByUserDTO request) {
        try {
            List<ListingDTO> listings = rabbitTemplate.convertSendAndReceiveAsType("", listingsQueueName, request,
                    new ParameterizedTypeReference<List<ListingDTO>>() {
                    });
            log.info("getAllListingsByGameByUser executed successfully.");
            return ResponseEntity.ok(listings);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/myListings")
    public ResponseEntity<List<ListingDTO>> getAllMyListingsByGame(@RequestBody GetMyListingsByGameDTO request) {
        try {
            List<ListingDTO> listings = rabbitTemplate.convertSendAndReceiveAsType("", listingsQueueName, request,
                    new ParameterizedTypeReference<List<ListingDTO>>() {
                    });
            log.info("getAllMyListingsByGame executed successfully.");
            return ResponseEntity.ok(listings);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<ListingDTO> createListing(@RequestBody ListingDTO request) {
        try {
            ListingDTO response = rabbitTemplate.convertSendAndReceiveAsType("", listingsQueueName, request,
                    new ParameterizedTypeReference<ListingDTO>() {
                    });
            log.info("createListing executed successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping
    public ResponseEntity<ListingDTO> updateListingState(@RequestBody ListingUpdateDTO request) {
        try {
            ListingDTO response = rabbitTemplate.convertSendAndReceiveAsType("", listingsQueueName, request,
                    new ParameterizedTypeReference<ListingDTO>() {
                    });
            log.info("updateListingState executed successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("controller/setMQ")
    public ResponseEntity<String> setMQ(@RequestBody String mqName) {
        try {
            listingsQueueName = mqName;
            log.info("setMQ executed successfully.");
            return ResponseEntity.ok("MQ set to " + mqName);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

}