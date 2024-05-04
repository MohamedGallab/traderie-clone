package com.massivelyflammableapps.offers.commands;

import java.util.List;

import com.massivelyflammableapps.offers.model.Offer;
import com.massivelyflammableapps.offers.service.OffersService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetAllOffersCommand extends AbstractCommand {
    private OffersService offersService;

    public List<Offer> execute() {
        return offersService.getAllOffers();
    }
}
