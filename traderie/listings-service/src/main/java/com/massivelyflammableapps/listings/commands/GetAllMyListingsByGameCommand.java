package com.massivelyflammableapps.listings.commands;

import com.massivelyflammableapps.shared.dto.listings.GetListingsByGameByUserDTO;
import com.massivelyflammableapps.listings.model.ListingByGameByUser;
import com.massivelyflammableapps.listings.service.ListingsService;
import com.massivelyflammableapps.shared.dto.listings.ListingDTO;
import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetAllMyListingsByGameCommand extends AbstractCommand<List<ListingDTO>> {
    @NonNull
    private ListingsService listingsService;
    @NonNull
    private GetListingsByGameByUserDTO GetListingsByGameByUserDTO;

    public List<ListingDTO> execute() {
        return listingsService.getAllMyListingsByGame(GetListingsByGameByUserDTO);
    }
}
