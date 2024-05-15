package com.massivelyflammableapps.offers.commands;

import java.util.List;
import java.util.UUID;

import com.massivelyflammableapps.offers.service.OffersService;
import com.massivelyflammableapps.shared.dto.offers.OfferDTO;

import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class GetOffersBySellerAndBuyerCommand extends AbstractCommand {
  @NonNull
  private OffersService offersService;
  @NonNull
  private UUID sellerId;
  @NonNull
  private UUID buyerId;

  public List<OfferDTO> execute() {
      return offersService.getOfferBySellerAndBuyer(sellerId,buyerId);
  }
}
