package com.massivelyflammableapps.listings.commands;

import com.massivelyflammableapps.listings.dto.GetListingsByGameByUserDTO;
import com.massivelyflammableapps.listings.dto.ListingUpdateDTO;
import com.massivelyflammableapps.listings.exceptions.UnauthorizedException;
import com.massivelyflammableapps.listings.model.ListingByGameByProduct;
import com.massivelyflammableapps.listings.model.ListingByGameByUser;
import com.massivelyflammableapps.listings.service.ListingsService;
import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateListingStateCommand extends AbstractCommand<ListingByGameByProduct> {
    @NonNull
    private ListingsService listingsService;
    @NonNull
    private ListingUpdateDTO listingUpdateDTO;

    public ListingByGameByProduct execute() throws UnauthorizedException {
        return listingsService.updateListingState(listingUpdateDTO);
    }
}
