package com.massivelyflammableapps.listings.commands;

import com.massivelyflammableapps.listings.service.ListingsService;
import com.massivelyflammableapps.shared.dto.listings.ListingDTO;
import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class CreateListingCommand extends AbstractCommand<ListingDTO> {
    @NonNull
    private ListingsService listingsService;
    @NonNull
    private ListingDTO createListingDTO;

    @Override
    public ListingDTO execute() {
        return listingsService.createListing(createListingDTO);
    }
}
