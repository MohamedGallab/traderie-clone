package com.massivelyflammableapps.webserver.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.massivelyflammableapps.shared.dto.offers.CreateOfferRequest;
import com.massivelyflammableapps.shared.dto.offers.GetAllOffersRequest;
import com.massivelyflammableapps.shared.dto.offers.GetOffersByBuyerRequest;
import com.massivelyflammableapps.shared.dto.offers.GetOffersByListingRequest;
import com.massivelyflammableapps.shared.dto.offers.GetOffersBySellerAndBuyerRequest;
import com.massivelyflammableapps.shared.dto.offers.GetOffersBySellerRequest;
import com.massivelyflammableapps.shared.dto.offers.OfferDTO;

@RestController
@RequestMapping("api/v1/offers")
public class OffersController {
    @Value("${offers-service.queue.name}")
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
            // AuthenticateJWTRequest command = new AuthenticateJWTRequest(token);
            // AuthenticateJWTResponse response = rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
            //         new ParameterizedTypeReference<AuthenticateJWTResponse>() {
            //         });
            // if not authenticated, return 401
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
    
    @GetMapping(params = { "listingId" })
    public ResponseEntity<List<OfferDTO>> getOfferByListing(@RequestParam UUID listingId) {
        try {
            GetOffersByListingRequest command = new GetOffersByListingRequest(listingId);
            List<OfferDTO> response = rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
                    new ParameterizedTypeReference< List<OfferDTO>>() {
                    });
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping(params = { "sellerId" })
    public ResponseEntity<List<OfferDTO>> getOfferBySeller(@RequestParam UUID sellerId) {
        try {
            GetOffersBySellerRequest command = new GetOffersBySellerRequest(sellerId);
            List<OfferDTO> response = rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
                    new ParameterizedTypeReference< List<OfferDTO>>() {
                    });
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping(params = { "buyerId" })
    public ResponseEntity<List<OfferDTO>> getOfferByBuyer(@RequestParam UUID buyerId) {
        try {
            GetOffersByBuyerRequest command = new GetOffersByBuyerRequest(buyerId);
            List<OfferDTO> response = rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
                    new ParameterizedTypeReference< List<OfferDTO>>() {
                    });
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping(params = { "sellerId", "buyerId" })
    public ResponseEntity<List<OfferDTO>> getOfferBySellerAndBuyer(@RequestParam UUID sellerId,
            @RequestParam UUID buyerId) {
        try {
            GetOffersBySellerAndBuyerRequest command = new GetOffersBySellerAndBuyerRequest(sellerId,buyerId);
            List<OfferDTO> response = rabbitTemplate.convertSendAndReceiveAsType("", queueName,command,
            new ParameterizedTypeReference<List<OfferDTO>>() {
            });
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}