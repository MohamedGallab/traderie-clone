package com.massivelyflammableapps.listings.commands;

import com.massivelyflammableapps.shared.dto.listings.GetListingsByGameByProductDTO;
import com.massivelyflammableapps.listings.service.ListingsService;
import com.massivelyflammableapps.shared.dto.listings.ListingDTO;
import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class GetAllListingsByGameByProductCommand extends AbstractCommand<List<ListingDTO>> {
    @NonNull
    private ListingsService listingsService;
    @NonNull
    private GetListingsByGameByProductDTO getListingsByGameByProductDTO;

    public List<ListingDTO> execute() {

        return listingsService.getAllListingsByGameByProduct(getListingsByGameByProductDTO);
    }
}
