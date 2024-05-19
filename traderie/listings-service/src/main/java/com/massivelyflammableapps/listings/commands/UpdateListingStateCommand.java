package com.massivelyflammableapps.listings.commands;

import com.massivelyflammableapps.shared.dto.listings.ListingDTO;
import com.massivelyflammableapps.shared.dto.listings.ListingUpdateDTO;
import com.massivelyflammableapps.shared.exceptions.UnauthorizedException;
import com.massivelyflammableapps.listings.service.ListingsService;
import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
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
