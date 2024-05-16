package com.massivelyflammableapps.shared.dto.offers;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDTO implements Serializable {
    private UUID id;
    private UUID listingId;
    private UUID buyerId;
    private UUID sellerId;
    private String timestamp;
    private String status;
    private List<List<OfferedProductDTO>> offeredProducts;
}
