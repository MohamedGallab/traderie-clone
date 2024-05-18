package com.massivelyflammableapps.listings.commands;


import com.massivelyflammableapps.listings.exceptions.UnauthorizedException;
import com.massivelyflammableapps.listings.service.ListingsService;
import com.massivelyflammableapps.shared.dto.listings.GetListingsByGameByProductDTO;
import com.massivelyflammableapps.shared.dto.listings.GetListingsByGameByUserDTO;
import com.massivelyflammableapps.shared.dto.listings.ListingDTO;
import com.massivelyflammableapps.shared.dto.listings.ListingUpdateDTO;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@RabbitListener(queues = {"${service.queue.name}"})
public class ListingsInvoker {
    @Autowired
    private ListingsService listingsService;

    /* thread pool ???????
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    @RabbitListener(queues = {"${rabbitmq.commandQueue.name}"})
    public <T> T executeC(AbstractCommand<T> message) throws UnauthorizedException {
        message.setListingsService(listingsService);
        return message.execute();
    }*/
    @RabbitHandler
    public List<ListingDTO> getAllListingsByGameByProduct(@Payload GetListingsByGameByProductDTO request){
        GetAllListingsByGameByProductCommand command = new GetAllListingsByGameByProductCommand(listingsService ,request);
        return command.execute();
    }
    @RabbitHandler
    public List<ListingDTO> getAllListingsByGameByUser(@Payload GetListingsByGameByUserDTO request) {
    GetAllListingsByGameByUserCommand command = new GetAllListingsByGameByUserCommand(listingsService ,request);
    return command.execute();
    }
    @RabbitHandler
    public List<ListingDTO> getAllMyListingsByGame(GetListingsByGameByUserDTO request) {
        GetAllMyListingsByGameCommand command = new GetAllMyListingsByGameCommand(listingsService ,request);
        return command.execute();
    }
    @RabbitHandler
    public ListingDTO createListing(ListingDTO request) {
        CreateListingCommand command = new CreateListingCommand(listingsService ,request);
        return command.execute();
    }
    @RabbitHandler
    public ListingDTO updateListingState(ListingUpdateDTO request) throws UnauthorizedException {
        UpdateListingStateCommand command = new UpdateListingStateCommand(listingsService ,request);
        return command.execute();
    }
}