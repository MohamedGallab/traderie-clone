package com.massivelyflammableapps.offers.commands;

import java.util.List;
import java.util.UUID;

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
public class GetOffersByListingCommand extends AbstractCommand<List<OfferDTO>> {
    @NonNull
    private OffersService offersService;
    @NonNull
    private UUID listingId;

    public List<OfferDTO> execute() {
        return offersService.getOfferByListing(listingId);
    }
}
