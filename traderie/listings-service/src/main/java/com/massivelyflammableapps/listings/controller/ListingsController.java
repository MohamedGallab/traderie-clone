package com.massivelyflammableapps.listings.controller;

import com.massivelyflammableapps.listings.commands.*;
import com.massivelyflammableapps.listings.dto.CreateListingDTO;
import com.massivelyflammableapps.listings.dto.GetListingsByGameByProductDTO;
import com.massivelyflammableapps.listings.dto.GetListingsByGameByUserDTO;
import com.massivelyflammableapps.listings.dto.ListingUpdateDTO;
import com.massivelyflammableapps.listings.exceptions.UnauthorizedException;
import com.massivelyflammableapps.listings.service.ListingsService;
import org.springframework.web.bind.annotation.*;
import com.massivelyflammableapps.listings.model.ListingByGameByProduct;
import com.massivelyflammableapps.listings.model.ListingByGameByUser;
import java.util.List;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("api/v1/listings")
public class ListingsController {

    @Autowired
    private ListingsService listingsService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    public ResponseEntity<List<ListingByGameByProduct>> getAllListingsByGameByProduct(@RequestBody GetListingsByGameByProductDTO request){
        try {
            AbstractCommand<List<ListingByGameByProduct>> command = new GetAllListingsByGameByProductCommand(listingsService ,request);
            List<ListingByGameByProduct> listings = rabbitTemplate.convertSendAndReceiveAsType("", "listingsQueue", command,
                    new ParameterizedTypeReference<List<ListingByGameByProduct>>() {
                    });
            return ResponseEntity.ok(listings);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ListingByGameByUser>> getAllListingsByGameByUser(@RequestBody GetListingsByGameByUserDTO request){
        try {
            AbstractCommand<List<ListingByGameByUser>> command = new GetAllListingsByGameByUserCommand(listingsService, request);
            List<ListingByGameByUser> listings = rabbitTemplate.convertSendAndReceiveAsType("", "listingsQueue", command,
                    new ParameterizedTypeReference<List<ListingByGameByUser>>() {
                    });
            return ResponseEntity.ok(listings);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ListingByGameByUser>> getAllMyListingsByGame(@RequestBody GetListingsByGameByUserDTO request){
        try {
            AbstractCommand<List<ListingByGameByUser>> command = new GetAllListingsByGameByUserCommand(listingsService, request);
            List<ListingByGameByUser> listings = rabbitTemplate.convertSendAndReceiveAsType("", "listingsQueue", command,
                    new ParameterizedTypeReference<List<ListingByGameByUser>>() {
                    });
            return ResponseEntity.ok(listings);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<ListingByGameByProduct> createListing(@RequestBody CreateListingDTO request) {
        try {
            AbstractCommand command = new CreateListingCommand(listingsService ,request);
            ListingByGameByProduct response = rabbitTemplate.convertSendAndReceiveAsType("", "listingsQueue", command,
                    new ParameterizedTypeReference<ListingByGameByProduct>() {
                    });
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping
    public ResponseEntity<ListingByGameByProduct> updateListingState(@RequestBody ListingUpdateDTO request) {
        try {
            AbstractCommand command = new UpdateListingStateCommand(listingsService ,request);
            ListingByGameByProduct response = rabbitTemplate.convertSendAndReceiveAsType("", "listingsQueue", command,
                    new ParameterizedTypeReference<ListingByGameByProduct>() {
                    });
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

}