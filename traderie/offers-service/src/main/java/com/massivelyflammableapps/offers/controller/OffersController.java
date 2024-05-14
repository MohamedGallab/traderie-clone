package com.massivelyflammableapps.offers.controller;

import org.springframework.web.bind.annotation.RestController;

import com.massivelyflammableapps.offers.commands.AbstractCommand;
import com.massivelyflammableapps.offers.commands.CreateOfferCommand;
import com.massivelyflammableapps.offers.commands.GetAllOffersCommand;
import com.massivelyflammableapps.offers.model.Offer;
import com.massivelyflammableapps.offers.model.OfferByListing;
import com.massivelyflammableapps.offers.model.OfferBySeller;
import com.massivelyflammableapps.offers.model.OfferByBuyer;
import com.massivelyflammableapps.offers.model.OfferBySellerAndBuyer;
import com.massivelyflammableapps.offers.service.OffersService;

import lombok.val;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/offers")
public class OffersController {

    @Autowired
    private OffersService offersService;

    @Value("${service.queue.name}")
    private String queueName;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    public ResponseEntity<List<Offer>> getAllOffers() {
        try {
            AbstractCommand command = new GetAllOffersCommand();
            List<Offer> offers = rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
                    new ParameterizedTypeReference<List<Offer>>() {
                    });
            return ResponseEntity.ok(offers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<Offer> createOffer(@RequestBody Offer request) {
        try {
            AbstractCommand command = new CreateOfferCommand(request);
            Offer response = rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
                    new ParameterizedTypeReference<Offer>() {
                    });
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping(params = { "listingId" })
    public ResponseEntity<List<OfferByListing>> getOfferByListing(@RequestParam UUID listingId) {
        try {
            List<OfferByListing> offers = offersService.getOfferByListing(listingId);
            System.err.println(listingId);
            return ResponseEntity.ok(offers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping(params = { "sellerId" })
    public ResponseEntity<List<OfferBySeller>> getOfferBySeller(@RequestParam UUID sellerId) {
        try {
            List<OfferBySeller> offers = offersService.getOfferBySeller(sellerId);
            return ResponseEntity.ok(offers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping(params = { "buyerId" })
    public ResponseEntity<List<OfferByBuyer>> getOfferByBuyer(@RequestParam UUID buyerId) {
        try {
            List<OfferByBuyer> offers = offersService.getOfferByBuyer(buyerId);
            return ResponseEntity.ok(offers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping(params = { "sellerId", "buyerId" })
    public ResponseEntity<List<OfferBySellerAndBuyer>> getOfferBySellerAndBuyer(@RequestParam UUID sellerId,
            @RequestParam UUID buyerId) {
        try {
            List<OfferBySellerAndBuyer> offers = offersService.getOfferBySellerAndBuyer(sellerId, buyerId);
            return ResponseEntity.ok(offers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}