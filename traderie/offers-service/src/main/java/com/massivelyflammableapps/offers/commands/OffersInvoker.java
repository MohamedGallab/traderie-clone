package com.massivelyflammableapps.offers.commands;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
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

@Service
@RabbitListener(queues = {"${service.queue.name}"})
public class OffersInvoker {
    @Autowired
    private OffersService offersService;

    private CommandHandler commandHandler = new CommandHandler();

    @RabbitHandler
    public List<OfferDTO> getAllOffers(@Payload GetAllOffersRequest request) {
        GetAllOffersCommand command = new GetAllOffersCommand(offersService);
        return command.execute();
    }

    @RabbitHandler
    public OfferDTO createOffer(@Payload CreateOfferRequest request) {
        CreateOfferCommand command = new CreateOfferCommand(offersService, request.getOffer());
        return command.execute();
    }

    @RabbitHandler
    public List<OfferDTO> getOffersByListing(@Payload GetOffersByListingRequest request) {
        GetOffersByListingCommand command = new GetOffersByListingCommand(offersService,request.getListingId());
        return command.execute();
    }

    @RabbitHandler
    public List<OfferDTO> getOffersByBuyer(@Payload GetOffersByBuyerRequest request) {
        GetOffersByBuyerCommand command = new GetOffersByBuyerCommand(offersService,request.getBuyerId());
        return command.execute();
    }

    @RabbitHandler
    public List<OfferDTO> getOffersBySeller(@Payload GetOffersBySellerRequest request) {
        GetOffersBySellerCommand command = new GetOffersBySellerCommand(offersService,request.getSellerId());
        return command.execute();
    }

    @RabbitHandler
    public List<OfferDTO> getOffersBySellerAndBuyer(@Payload GetOffersBySellerAndBuyerRequest request) {
        GetOffersBySellerAndBuyerCommand command = new GetOffersBySellerAndBuyerCommand(offersService,request.getSellerId(),request.getBuyerId());
        return command.execute();
    }

    @RabbitHandler
    public Boolean addCommand(@Payload AddCommandRequest request) {
        return commandHandler.createCommandFile(request.getCommandClass(), request.getCommandCode());
    }

    @RabbitHandler
    public Boolean deleteCommand(@Payload DeleteCommandRequest request) {
        return commandHandler.deleteCommandFile(request.getCommandClass());
    }

    @RabbitHandler
    public Boolean updateCommand(@Payload UpdateCommandRequest request) {
        boolean deleteResult = commandHandler.deleteCommandFile(request.getCommandClass());
        if (!deleteResult) {
            return false;
        }
        return commandHandler.createCommandFile(request.getCommandClass(), request.getCommandCode());
    }
    
    @RabbitHandler
    public Object executeCommand(@Payload ExecuteCommandRequest request) {
        var result = commandHandler.runIt(request.getCommandClass(), request.getParamsObj());
        if (result == null) {
            return "void";
        }
        return result;
    }
}
