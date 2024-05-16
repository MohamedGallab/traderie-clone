package com.massivelyflammableapps.offers.commands;

import java.util.List;
import java.util.UUID;

import com.massivelyflammableapps.offers.service.OffersService;
import com.massivelyflammableapps.shared.dto.offers.OfferDTO;

import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class GetOffersByListingCommand extends AbstractCommand {
    @NonNull
    private OffersService offersService;
    @NonNull
    private UUID listingId;

    public List<OfferDTO> execute() {
        return offersService.getOfferByListing(listingId);
    }
}
