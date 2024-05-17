package com.massivelyflammableapps.offers.commands;

import java.util.List;
import java.util.UUID;

import com.massivelyflammableapps.offers.service.OffersService;
import com.massivelyflammableapps.shared.dto.offers.OfferDTO;

import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RequiredArgsConstructor
public class GetOffersBySellerCommand extends AbstractCommand<List<OfferDTO>>{
    @NonNull
    private OffersService offersService;
    @NonNull
    private UUID sellerId;

    public List<OfferDTO> execute() {
        return offersService.getOfferBySeller(sellerId);
    }
}
