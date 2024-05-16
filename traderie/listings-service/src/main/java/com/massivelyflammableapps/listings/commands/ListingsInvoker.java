package com.massivelyflammableapps.listings.commands;

import com.massivelyflammableapps.listings.exceptions.UnauthorizedException;
import com.massivelyflammableapps.listings.service.ListingsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class ListingsInvoker {
    @Autowired
    private ListingsService listingsService;

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());



    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public <T> T execute(AbstractCommand<T> message) throws UnauthorizedException {
        message.setListingsService(listingsService);
        executor.execute(message);
        // TODO Return
        return message.execute();
    }

    @RabbitListener(queues = {"${rabbitmq.commandQueue.name}"})
    public <T> T executeC(AbstractCommand<T> message) throws UnauthorizedException {
        message.setListingsService(listingsService);
        return message.execute();
    }
}
