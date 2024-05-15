package com.massivelyflammableapps.offers.commands;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.massivelyflammableapps.offers.model.Offer;
import com.massivelyflammableapps.offers.service.OffersService;
import com.massivelyflammableapps.shared.dto.offers.CreateOfferRequest;
import com.massivelyflammableapps.shared.dto.offers.GetAllOffersRequest;
import com.massivelyflammableapps.shared.dto.offers.OfferDTO;

@Service
@RabbitListener(queues = {"${service.queue.name}"})
public class OffersInvoker {
    @Autowired
    private OffersService offersService;

    @RabbitHandler
    public List<Offer> getAllOffers(@Payload GetAllOffersRequest request) {
        AbstractCommand command = new GetAllOffersCommand(offersService);
        return command.execute();
    }

    @RabbitHandler
    public OfferDTO createOffer(@Payload CreateOfferRequest request) {
        AbstractCommand command = new CreateOfferCommand(offersService, request.getOffer());
        return command.execute();
    }

    

    // public <T> T execute(AbstractCommand message) {
    //     message.setOffersService(offersService);
    //     return message.execute();
    // }
}
