package com.massivelyflammableapps.offers;

import lombok.*;

@Data
@Builder
public class Offer {
    private String id;
    private String listingId;
    private String buyerId;
    private String sellerId;
    
    // timestamp timestamp,
    // status string,
    // offered_products set<frozen<set<frozen<offered_product>>>>
}
