package com.massivelyflammableapps.shared.dto.listings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.massivelyflammableapps.shared.resources.STATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ListingDTO implements Serializable {
    private UUID userId;
    private UUID gameId;
    private UUID productId;
    private boolean buying;
    private String timestamp;
    private UUID listingId;
    private String productName;
    private String productIcon;
    private int quantity;
    private List<List<ProductAmountDTO>> desiredOffer;
    private STATE state;
}
