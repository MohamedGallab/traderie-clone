package com.massivelyflammableapps.offers.commands;

import java.util.List;
import java.util.UUID;

import com.massivelyflammableapps.offers.model.OfferByBuyer;
import com.massivelyflammableapps.offers.service.OffersService;

import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class GetOffersByBuyerCommand extends AbstractCommand{
    @NonNull
    private OffersService offersService;
    @NonNull
    private UUID buyerId;

    public List<OfferByBuyer> execute() {
        return offersService.getOfferByBuyer(buyerId);
    }
}
