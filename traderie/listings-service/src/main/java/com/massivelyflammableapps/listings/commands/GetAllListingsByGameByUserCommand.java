package com.massivelyflammableapps.listings.commands;

import com.massivelyflammableapps.listings.dto.GetListingsByGameByProductDTO;
import com.massivelyflammableapps.listings.dto.GetListingsByGameByUserDTO;
import com.massivelyflammableapps.listings.model.ListingByGameByProduct;
import com.massivelyflammableapps.listings.model.ListingByGameByUser;
import com.massivelyflammableapps.listings.repository.ListingsByGameByUserRepository;
import com.massivelyflammableapps.listings.service.ListingsService;
import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class GetAllListingsByGameByUserCommand extends AbstractCommand<List<ListingByGameByUser>> {
    @NonNull
    private ListingsService listingsService;
    @NonNull
    private GetListingsByGameByUserDTO getListingsByGameByUserDTO;

    public List<ListingByGameByUser> execute() {
        return listingsService.getAllListingsByGameByUser(getListingsByGameByUserDTO);
    }
}
