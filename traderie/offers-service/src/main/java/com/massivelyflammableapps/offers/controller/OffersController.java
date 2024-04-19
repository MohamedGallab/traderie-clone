package com.massivelyflammableapps.offers.controller;

import org.springframework.web.bind.annotation.RestController;

import com.massivelyflammableapps.offers.model.Offer;
import com.massivelyflammableapps.offers.repository.OffersRepository;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
    public ResponseEntity<List<Offer>> getMethodName() {
        return offersRepository.findAll().isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(offersRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Offer> postMethodName(@RequestBody Offer request) {
        try {
            Offer newOffer = new Offer(
                    request.getListingId(),
                    request.getBuyerId(),
                    request.getSellerId(),
                    request.getStatus(),
                    request.getOfferedProducts());

            Offer response = offersRepository.save(newOffer);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}