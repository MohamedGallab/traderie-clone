package com.massivelyflammableapps.offers.commands;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.massivelyflammableapps.offers.service.OffersService;

@Service
public class OffersInvoker {
    @Autowired
    private OffersService offersService;

    @RabbitListener(queues = {"${service.queue.name}"})
    public <T> T execute(AbstractCommand message) {
        message.setOffersService(offersService);
        return message.execute();
    }
}
