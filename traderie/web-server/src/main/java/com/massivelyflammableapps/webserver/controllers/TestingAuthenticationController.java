package com.massivelyflammableapps.webserver.controllers;

import com.massivelyflammableapps.shared.dto.user.UserDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/test")
public class TestingAuthenticationController {
    @Autowired
    private ExternalServiceClient externalServiceClient
    ;

    @Value("${offers-service.queue.name}")
    private String offersQueueName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    public ResponseEntity<UserDto> test(
            @RequestHeader("UUID") String uuid) {
        try {
            System.out.println(uuid);
//            GetAllOffersRequest command = new GetAllOffersRequest();
//            List<OfferDTO> offers = rabbitTemplate.convertSendAndReceiveAsType("", offersQueueName, command,
//                    new ParameterizedTypeReference<List<OfferDTO>>() {
//                   });
            UserDto resp =  externalServiceClient.getDataFromExternalService("Hala Medhat");
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


}