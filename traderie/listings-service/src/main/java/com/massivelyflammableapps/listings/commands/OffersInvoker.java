package com.massivelyflammableapps.listings.commands;

import com.massivelyflammableapps.listings.exceptions.UnauthorizedException;
import com.massivelyflammableapps.listings.service.ListingsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OffersInvoker {
    @Autowired
    private ListingsService listingsService;

    @RabbitListener(queues = {"listings-queue"})
    public <T> T execute(AbstractCommand<T> message) throws UnauthorizedException {
        message.setListingsService(listingsService);
        return message.execute();
    }
}
