package com.massivelyflammableapps.shared.dto.listings;

import com.massivelyflammableapps.resources.STATE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListingDTO implements Serializable {
    private UUID userId;
    private UUID gameId;
    private UUID productId;
    private boolean buying;
    private String timestamp = new Date().toString();
    private UUID listingId;
    private String productName ;
    private String productIcon ;
    private int quantity ;
    private List< List<ProductAmountDTO>> desiredOffer;
    private STATE state ;
}
