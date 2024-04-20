package com.massivelyflammableapps.offers.controller;

import org.springframework.web.bind.annotation.RestController;

import com.massivelyflammableapps.offers.model.Offer;
import com.massivelyflammableapps.offers.model.OfferByListing;
import com.massivelyflammableapps.offers.model.OfferBySeller;
import com.massivelyflammableapps.offers.model.OfferByBuyer;
import com.massivelyflammableapps.offers.model.OfferBySellerAndBuyer;
import com.massivelyflammableapps.offers.service.OffersService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/offers")
public class OffersController {

    @Autowired
    private OffersService offersService;

    @GetMapping
    public ResponseEntity<List<Offer>> getAllOffers() {
        List<Offer> offers = offersService.getAllOffers();
        return ResponseEntity.ok(offers);
    }

    @PostMapping
    public ResponseEntity<Offer> createOffer(@RequestBody Offer request) {
        try {
            Offer response = offersService.createOffer(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<OfferByListing>> getOfferByListing(@RequestParam UUID listingId) {
        try {
            List<OfferByListing> offers = offersService.getOfferByListing(listingId);
            return ResponseEntity.ok(offers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<OfferBySeller>> getOfferBySeller(@RequestParam UUID sellerId) {
        try {
            List<OfferBySeller> offers = offersService.getOfferBySeller(sellerId);
            return ResponseEntity.ok(offers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<OfferByBuyer>> getOfferByBuyer(@RequestParam UUID buyerId) {
        try {
            List<OfferByBuyer> offers = offersService.getOfferByBuyer(buyerId);
            return ResponseEntity.ok(offers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping
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