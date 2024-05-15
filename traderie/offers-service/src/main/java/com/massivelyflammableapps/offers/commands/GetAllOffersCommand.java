package com.massivelyflammableapps.offers.commands;

import java.util.List;

import com.massivelyflammableapps.offers.model.Offer;
import com.massivelyflammableapps.offers.service.OffersService;
import com.massivelyflammableapps.shared.dto.offers.OfferDTO;

import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GetAllOffersCommand extends AbstractCommand {
    @NonNull
    private OffersService offersService;

    public List<OfferDTO> execute() {
        return offersService.getAllOffers();
    }
}
