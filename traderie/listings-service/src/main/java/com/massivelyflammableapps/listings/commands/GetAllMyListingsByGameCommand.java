package com.massivelyflammableapps.listings.commands;

import com.massivelyflammableapps.listings.dto.GetListingsByGameByUserDTO;
import com.massivelyflammableapps.listings.model.ListingByGameByUser;
import com.massivelyflammableapps.listings.service.ListingsService;
import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class GetAllMyListingsByGameCommand extends AbstractCommand<List<ListingByGameByUser>> {
    @NonNull
    private ListingsService listingsService;
    @NonNull
    private GetListingsByGameByUserDTO GetListingsByGameByUserDTO;

    public List<ListingByGameByUser> execute() {
        return listingsService.getAllMyListingsByGame(GetListingsByGameByUserDTO);
    }
}
