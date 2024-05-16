package com.massivelyflammableapps.offers.commands;

import java.util.List;

import com.massivelyflammableapps.offers.service.OffersService;
import com.massivelyflammableapps.shared.dto.offers.OfferDTO;

import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class GetAllOffersCommand extends AbstractCommand<List<OfferDTO>> {
    @NonNull
    private OffersService offersService;

    public List<OfferDTO> execute() {
        return offersService.getAllOffers();
    }
}
