package com.massivelyflammableapps.listings.commands;

import com.massivelyflammableapps.listings.dto.GetListingsByGameByProductDTO;
import com.massivelyflammableapps.listings.model.ListingByGameByProduct;
import com.massivelyflammableapps.listings.service.ListingsService;
import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor

public class GetAllListingsByGameByProductCommand extends AbstractCommand<List<ListingByGameByProduct>> {
    @NonNull
    private ListingsService listingsService;
    @NonNull
    private GetListingsByGameByProductDTO getListingsByGameByProductDTO;

    public List<ListingByGameByProduct> execute() {
        return listingsService.getAllListingsByGameByProduct(getListingsByGameByProductDTO);
    }
}
