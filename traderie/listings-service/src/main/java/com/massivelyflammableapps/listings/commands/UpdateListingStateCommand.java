package com.massivelyflammableapps.listings.commands;

import com.massivelyflammableapps.shared.dto.listings.ListingDTO;
import com.massivelyflammableapps.shared.dto.listings.ListingUpdateDTO;
import com.massivelyflammableapps.listings.exceptions.UnauthorizedException;
import com.massivelyflammableapps.listings.model.ListingByGameByProduct;
import com.massivelyflammableapps.listings.service.ListingsService;
import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateListingStateCommand extends AbstractCommand<ListingDTO> {
    @NonNull
    private ListingsService listingsService;
    @NonNull
    private ListingUpdateDTO listingUpdateDTO;

    public ListingDTO execute() throws UnauthorizedException {
        return listingsService.updateListingState(listingUpdateDTO);
    }
}
