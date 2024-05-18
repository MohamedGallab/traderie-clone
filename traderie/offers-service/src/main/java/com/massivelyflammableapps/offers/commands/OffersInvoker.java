package com.massivelyflammableapps.offers.commands;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.massivelyflammableapps.offers.service.OffersService;
import com.massivelyflammableapps.shared.CommandHandler;
import com.massivelyflammableapps.shared.dto.AddCommandRequest;
import com.massivelyflammableapps.shared.dto.DeleteCommandRequest;
import com.massivelyflammableapps.shared.dto.ExecuteCommandRequest;
import com.massivelyflammableapps.shared.dto.UpdateCommandRequest;
import com.massivelyflammableapps.shared.dto.offers.CreateOfferRequest;
import com.massivelyflammableapps.shared.dto.offers.GetAllOffersRequest;
import com.massivelyflammableapps.shared.dto.offers.GetOffersByBuyerRequest;
import com.massivelyflammableapps.shared.dto.offers.GetOffersByListingRequest;
import com.massivelyflammableapps.shared.dto.offers.GetOffersBySellerAndBuyerRequest;
import com.massivelyflammableapps.shared.dto.offers.GetOffersBySellerRequest;
import com.massivelyflammableapps.shared.dto.offers.OfferDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RabbitListener(queues = { "${service.queue.name}" })
public class OffersInvoker {

    @Autowired
    private OffersService offersService;

    private CommandHandler commandHandler = new CommandHandler();

    @Async
    @RabbitHandler
    public CompletableFuture<List<OfferDTO>> getAllOffers(@Payload GetAllOffersRequest request) {
        return CompletableFuture.completedFuture(
                new GetAllOffersCommand(offersService).execute());
    }

    @Async
    @RabbitHandler
    public CompletableFuture<OfferDTO> createOffer(@Payload CreateOfferRequest request) {
        return CompletableFuture.completedFuture(
                new CreateOfferCommand(offersService, request.getOffer()).execute());
    }

    @Async
    @RabbitHandler
    public CompletableFuture<List<OfferDTO>> getOffersByListing(@Payload GetOffersByListingRequest request) {
        return CompletableFuture.completedFuture(
                new GetOffersByListingCommand(offersService, request.getListingId()).execute());
    }

    @Async
    @RabbitHandler
    public CompletableFuture<List<OfferDTO>> getOffersByBuyer(@Payload GetOffersByBuyerRequest request) {
        return CompletableFuture.completedFuture(
                new GetOffersByBuyerCommand(offersService, request.getBuyerId()).execute());
    }

    @Async
    @RabbitHandler
    public CompletableFuture<List<OfferDTO>> getOffersBySeller(@Payload GetOffersBySellerRequest request) {
        return CompletableFuture.completedFuture(
                new GetOffersBySellerCommand(offersService, request.getSellerId()).execute());
    }

    @Async
    @RabbitHandler
    public CompletableFuture<List<OfferDTO>> getOffersBySellerAndBuyer(
            @Payload GetOffersBySellerAndBuyerRequest request) {
        return CompletableFuture.completedFuture(
                new GetOffersBySellerAndBuyerCommand(offersService, request.getSellerId(), request.getBuyerId())
                        .execute());
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Boolean> addCommand(@Payload AddCommandRequest request) {
        return CompletableFuture.completedFuture(
                commandHandler.createCommandFile(request.getCommandClass(), request.getCommandCode()));
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Boolean> deleteCommand(@Payload DeleteCommandRequest request) {
        return CompletableFuture.completedFuture(
                commandHandler.deleteCommandFile(request.getCommandClass()));
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Boolean> updateCommand(@Payload UpdateCommandRequest request) {
        boolean deleteResult = commandHandler.deleteCommandFile(request.getCommandClass());
        if (!deleteResult) {
            return CompletableFuture.completedFuture(false);
        }
        return CompletableFuture.completedFuture(
                commandHandler.createCommandFile(request.getCommandClass(), request.getCommandCode()));
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Object> executeCommand(@Payload ExecuteCommandRequest request) {
        Object result = commandHandler.runIt(request.getCommandClass(), request.getParamsObj());
        if (result == null) {
            return CompletableFuture.completedFuture("void");
        }
        return CompletableFuture.completedFuture(result);
    }
}
