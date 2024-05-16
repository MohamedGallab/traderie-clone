package com.massivelyflammableapps.webserver.controllers;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.massivelyflammableapps.shared.dto.offers.CreateOfferRequest;
import com.massivelyflammableapps.shared.dto.offers.GetAllOffersRequest;
import com.massivelyflammableapps.shared.dto.offers.OfferDTO;

@RestController
@RequestMapping("api/v1/offers")
public class OffersController {
    @Value("${service.queue.name}")
    private String queueName;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    public ResponseEntity<List<OfferDTO>> getAllOffers() {
        try {
            GetAllOffersRequest command = new GetAllOffersRequest();
            List<OfferDTO> offers = rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
                    new ParameterizedTypeReference<List<OfferDTO>>() {
                    });
            return ResponseEntity.ok(offers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<OfferDTO> createOffer(@RequestBody OfferDTO request) {
        try {
            CreateOfferRequest command = new CreateOfferRequest(request);
            OfferDTO response = rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
                    new ParameterizedTypeReference<OfferDTO>() {
                    });
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}