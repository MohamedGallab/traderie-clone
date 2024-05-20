package com.massivelyflammableapps.offers.commands;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.massivelyflammableapps.offers.service.OffersService;
import com.massivelyflammableapps.shared.CommandHandler;
import com.massivelyflammableapps.shared.dto.admin.AddCommandRequest;
import com.massivelyflammableapps.shared.dto.admin.DeleteCommandRequest;
import com.massivelyflammableapps.shared.dto.admin.ExecuteCommandRequest;
import com.massivelyflammableapps.shared.dto.admin.UpdateCommandRequest;
import com.massivelyflammableapps.shared.dto.offers.CreateOfferRequest;
import com.massivelyflammableapps.shared.dto.offers.GetAllOffersRequest;
import com.massivelyflammableapps.shared.dto.offers.GetOffersByBuyerRequest;
import com.massivelyflammableapps.shared.dto.offers.GetOffersByListingRequest;
import com.massivelyflammableapps.shared.dto.offers.GetOffersBySellerAndBuyerRequest;
import com.massivelyflammableapps.shared.dto.offers.GetOffersBySellerRequest;
import com.massivelyflammableapps.shared.dto.offers.OfferDTO;

import java.util.ArrayList;
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
        return CompletableFuture.supplyAsync(() -> {
            try {
                return new GetAllOffersCommand(offersService).execute();
            } catch (Exception e) {
                return new ArrayList<>();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<OfferDTO> createOffer(@Payload CreateOfferRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return new CreateOfferCommand(offersService, request.getOffer()).execute();
            } catch (Exception e) {
                return new OfferDTO();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<List<OfferDTO>> getOffersByListing(@Payload GetOffersByListingRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return new GetOffersByListingCommand(offersService, request.getListingId()).execute();
            } catch (Exception e) {
                return new ArrayList<>();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<List<OfferDTO>> getOffersByBuyer(@Payload GetOffersByBuyerRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return new GetOffersByBuyerCommand(offersService, request.getBuyerId()).execute();
            } catch (Exception e) {
                return new ArrayList<>();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<List<OfferDTO>> getOffersBySeller(@Payload GetOffersBySellerRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return new GetOffersBySellerCommand(offersService, request.getSellerId()).execute();
            } catch (Exception e) {
                return new ArrayList<>();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<List<OfferDTO>> getOffersBySellerAndBuyer(
            @Payload GetOffersBySellerAndBuyerRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return new GetOffersBySellerAndBuyerCommand(offersService, request.getSellerId(), request.getBuyerId())
                        .execute();
            } catch (Exception e) {
                return new ArrayList<>();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Boolean> addCommand(@Payload AddCommandRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return commandHandler.createCommandFile(request.getCommandClass(), request.getCommandCode());
            } catch (Exception e) {
                return false;
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Boolean> deleteCommand(@Payload DeleteCommandRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return commandHandler.deleteCommandFile(request.getCommandClass());
            } catch (Exception e) {
                return false;
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Boolean> updateCommand(@Payload UpdateCommandRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                boolean deleteResult = commandHandler.deleteCommandFile(request.getCommandClass());
                if (!deleteResult) {
                    return false;
                }
                return commandHandler.createCommandFile(request.getCommandClass(), request.getCommandCode());
            } catch (Exception e) {
                return false;
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Object> executeCommand(@Payload ExecuteCommandRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Object result = commandHandler.runIt(request.getCommandClass(), request.getParamsObj());
                if (result == null) {
                    return "void";
                }
                return result;
            } catch (Exception e) {
                return false;
            }
        });
    }
}
