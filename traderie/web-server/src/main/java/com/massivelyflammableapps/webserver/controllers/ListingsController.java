package com.massivelyflammableapps.webserver.controllers;


import com.massivelyflammableapps.shared.dto.listings.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("api/v1/listings")
public class ListingsController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    public ResponseEntity<List<ListingDTO>> getAllListingsByGameByProduct(@RequestBody GetListingsByGameByProductDTO request){
        try {

            List<ListingDTO> listings = rabbitTemplate.convertSendAndReceiveAsType("", "listingsQueue", request,
                    new ParameterizedTypeReference<List<ListingDTO>>() {
                    });
            return ResponseEntity.ok(listings);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ListingDTO>> getAllListingsByGameByUser(@RequestBody GetListingsByGameByUserDTO request){
        try {

            List<ListingDTO> listings = rabbitTemplate.convertSendAndReceiveAsType("", "listingsQueue", request,
                    new ParameterizedTypeReference<List<ListingDTO>>() {
                    });
            return ResponseEntity.ok(listings);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ListingDTO>> getAllMyListingsByGame(@RequestBody GetListingsByGameByUserDTO request){
        try {

            List<ListingDTO> listings = rabbitTemplate.convertSendAndReceiveAsType("", "listingsQueue", request,
                    new ParameterizedTypeReference<List<ListingDTO>>() {
                    });
            return ResponseEntity.ok(listings);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<ListingDTO> createListing(@RequestBody ListingDTO request) {
        try {

            ListingDTO response = rabbitTemplate.convertSendAndReceiveAsType("", "listingsQueue", request,
                    new ParameterizedTypeReference<ListingDTO>() {
                    });
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping
    public ResponseEntity<ListingDTO> updateListingState(@RequestBody ListingUpdateDTO request) {
        try {

            ListingDTO response = rabbitTemplate.convertSendAndReceiveAsType("", "listingsQueue", request,
                    new ParameterizedTypeReference<ListingDTO>() {
                    });
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

}