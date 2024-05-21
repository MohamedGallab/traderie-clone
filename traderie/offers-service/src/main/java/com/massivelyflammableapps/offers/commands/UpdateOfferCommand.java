
package com.massivelyflammableapps.offers.commands;

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
@RequiredArgsConstructor
@NoArgsConstructor
public class UpdateOfferCommand extends AbstractCommand<OfferDTO> {
    @NonNull
    private OffersService offersService;
    @NonNull
    private UUID offerId;
    @NonNull
    private String status;

    @Override
    public OfferDTO execute() {
        return offersService.updateOfferStatus(offerId, status);    
    }
}