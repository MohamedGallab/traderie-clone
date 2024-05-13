package com.massivelyflammableapps.listings.commands;

import com.massivelyflammableapps.listings.dto.GetListingsByGameByProductDTO;
import com.massivelyflammableapps.listings.model.ListingByGameByProduct;
import com.massivelyflammableapps.listings.service.ListingsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllListingsByGameByProductCommand extends AbstractCommand<List<ListingByGameByProduct>> {
    private ListingsService listingsService;
    private GetListingsByGameByProductDTO getListingsByGameByProductDTO;

    public List<ListingByGameByProduct> execute() {
        return listingsService.getAllListingsByGameByProduct(getListingsByGameByProductDTO);
    }
}
