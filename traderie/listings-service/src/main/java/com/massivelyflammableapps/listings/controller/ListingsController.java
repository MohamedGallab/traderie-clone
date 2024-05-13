package com.massivelyflammableapps.listings.controller;

import com.massivelyflammableapps.listings.service.ListingsService;
import org.springframework.web.bind.annotation.RestController;

import com.massivelyflammableapps.listings.commands.AbstractCommand;
import com.massivelyflammableapps.listings.commands.CreateListingCommand;
import com.massivelyflammableapps.listings.commands.GetAllListingsByGameByProductCommand;
import com.massivelyflammableapps.listings.commands.GetAllListingsByGameByUserCommand;
import com.massivelyflammableapps.listings.commands.GetAllMyListingsByGameCommand;
import com.massivelyflammableapps.listings.commands.OffersInvoker;
import com.massivelyflammableapps.listings.commands.UpdateListingStateCommand;
import com.massivelyflammableapps.listings.model.ListingByGameByProduct;
import com.massivelyflammableapps.listings.model.ListingByGameByUser;

import lombok.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.UUID;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/listings")
public class ListingsController {

    @Autowired
    private ListingsService listingsService;

    @Autowired
    private RabbitTemplate rabbitTemplate;
}