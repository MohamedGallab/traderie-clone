package com.massivelyflammableapps.listings.commands;

import com.massivelyflammableapps.listings.dto.GetListingsByGameByUserDTO;
import com.massivelyflammableapps.listings.model.ListingByGameByUser;
import com.massivelyflammableapps.listings.service.ListingsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllMyListingsByGameCommand extends AbstractCommand<List<ListingByGameByUser>> {
    private ListingsService listingsService;
    private GetListingsByGameByUserDTO GetListingsByGameByUserDTO;

    public List<ListingByGameByUser> execute() {
        return listingsService.getAllMyListingsByGame(GetListingsByGameByUserDTO);
    }
}
