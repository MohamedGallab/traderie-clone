package com.massivelyflammableapps.listings.commands;

import com.massivelyflammableapps.listings.service.ListingsService;
import com.massivelyflammableapps.shared.CommandHandler;
import com.massivelyflammableapps.shared.dto.admin.AddCommandRequest;
import com.massivelyflammableapps.shared.dto.admin.DeleteCommandRequest;
import com.massivelyflammableapps.shared.dto.admin.ExecuteCommandRequest;
import com.massivelyflammableapps.shared.dto.admin.UpdateCommandRequest;
import com.massivelyflammableapps.shared.dto.listings.GetListingsByGameByProductDTO;
import com.massivelyflammableapps.shared.dto.listings.GetListingsByGameByUserDTO;
import com.massivelyflammableapps.shared.dto.listings.GetMyListingsByGameDTO;
import com.massivelyflammableapps.shared.dto.listings.ListingDTO;
import com.massivelyflammableapps.shared.dto.listings.ListingUpdateDTO;
import com.massivelyflammableapps.shared.dto.listings.MarkListingDTO;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RabbitListener(queues = { "${service.queue.name}", "${service.queue.name}" + "_admin", "testQueue" })
public class ListingsInvoker {
    @Autowired
    private ListingsService listingsService;

    private CommandHandler commandHandler = new CommandHandler();

    @Async
    @RabbitHandler
    public CompletableFuture<List<ListingDTO>> getAllListingsByGameByProduct(
            @Payload GetListingsByGameByProductDTO request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                GetAllListingsByGameByProductCommand command = new GetAllListingsByGameByProductCommand(listingsService,
                        request);
                return command.execute();
            } catch (Exception e) {
                return new ArrayList<>();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<List<ListingDTO>> getAllListingsByGameByUser(@Payload GetListingsByGameByUserDTO request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                GetAllListingsByGameByUserCommand command = new GetAllListingsByGameByUserCommand(listingsService,
                        request);
                return command.execute();
            } catch (Exception e) {
                return new ArrayList<>();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<List<ListingDTO>> getAllMyListingsByGame(@Payload GetMyListingsByGameDTO request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                GetAllMyListingsByGameCommand command = new GetAllMyListingsByGameCommand(listingsService, request);
                return command.execute();
            } catch (Exception e) {
                return new ArrayList<>();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<ListingDTO> createListing(ListingDTO request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                CreateListingCommand command = new CreateListingCommand(listingsService, request);
                return command.execute();
            } catch (Exception e) {
                return new ListingDTO();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<ListingDTO> updateListingState(ListingUpdateDTO request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                UpdateListingStateCommand command = new UpdateListingStateCommand(listingsService, request);
                return command.execute();
            } catch (Exception e) {
                return new ListingDTO();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Boolean> markListing(@Payload MarkListingDTO request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                MarkListingCommand command = new MarkListingCommand(listingsService, request);
                return command.execute();
            } catch (Exception e) {
                return false;
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
