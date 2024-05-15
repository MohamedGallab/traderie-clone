package com.massivelyflammableapps.offers.commands;

import com.massivelyflammableapps.offers.model.Offer;
import com.massivelyflammableapps.offers.service.OffersService;

import edu.umd.cs.findbugs.annotations.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class CreateOfferCommand extends AbstractCommand {
    private OffersService offersService;
    @NonNull
    private Offer offer;
    
    @Override
    public Offer execute() {
        return offersService.createOffer(offer);
    }
}
