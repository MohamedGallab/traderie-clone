package com.massivelyflammableapps.listings.commands;

import com.massivelyflammableapps.listings.service.ListingsService;

import com.massivelyflammableapps.shared.dto.listings.MarkListingDTO;

import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class MarkListingCommand extends AbstractCommand<Boolean> {
    @NonNull
    private ListingsService listingsService;
    @NonNull
    private MarkListingDTO markListingDTO;

    public Boolean execute() {
        return listingsService.markListing(markListingDTO);
    }
}
