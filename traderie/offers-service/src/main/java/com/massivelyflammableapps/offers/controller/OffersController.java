package com.massivelyflammableapps.offers.controller;

import org.springframework.web.bind.annotation.RestController;

import com.massivelyflammableapps.offers.dto.MakeOfferRequest;
import com.massivelyflammableapps.offers.model.Offer;
import com.massivelyflammableapps.offers.repository.OffersRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    OffersRepository offersRepository;

    @GetMapping
    public String getMethodName(@RequestParam(required = false) String param) {
        return "yay you made it";
    }

    @PostMapping
    public ResponseEntity<Offer> postMethodName(@RequestBody Offer request) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            Offer newOffer = new Offer(
                    UUID.randomUUID(),
                    request.getListingId(),
                    request.getBuyerId(),
                    request.getSellerId(),
                    LocalDateTime.now().format(formatter),
                    request.getStatus(),
                    request.getOfferedProducts());

            Offer response = offersRepository.save(newOffer);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            // Return an error response entity
            return ResponseEntity.status(500).build();
        }
    }
}