package com.massivelyflammableapps.offers.commands;

import com.massivelyflammableapps.offers.service.OffersService;
import com.massivelyflammableapps.shared.dto.offers.OfferDTO;

import edu.umd.cs.findbugs.annotations.NonNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@NoArgsConstructor
public class CreateOfferCommand extends AbstractCommand<OfferDTO> {
    @NonNull
    private OffersService offersService;
    @NonNull
    private OfferDTO offer;
    
    @Override
    public OfferDTO execute() {
        return offersService.createOffer(offer);
    }
}
