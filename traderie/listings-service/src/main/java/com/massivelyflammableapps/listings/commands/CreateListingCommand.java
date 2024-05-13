package com.massivelyflammableapps.listings.commands;

import com.massivelyflammableapps.listings.dto.CreateListingDTO;
import com.massivelyflammableapps.listings.model.ListingByGameByProduct;
import com.massivelyflammableapps.listings.service.ListingsService;
import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class CreateListingCommand extends AbstractCommand<ListingByGameByProduct> {
    private ListingsService listingsService;
    @NonNull
    private CreateListingDTO createListingDTO;
    
    @Override
    public ListingByGameByProduct execute() {
        return listingsService.createListing(createListingDTO);
    }
}
