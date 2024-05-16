package com.massivelyflammableapps.offers.commands;

import com.massivelyflammableapps.offers.model.Offer;
import com.massivelyflammableapps.offers.service.OffersService;
import com.massivelyflammableapps.shared.dto.offers.OfferDTO;

import edu.umd.cs.findbugs.annotations.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class CreateOfferCommand extends AbstractCommand {
    @NonNull
    private OffersService offersService;
    @NonNull
    private OfferDTO offer;
    
    @Override
    public OfferDTO execute() {
        return offersService.createOffer(offer);
    }
}
