package com.massivelyflammableapps.offers;

import lombok.*;
import java.util.UUID;


import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Offer {
    @PrimaryKey
    private UUID id;
    private String listingId;
    private String buyerId;
    private String sellerId;
    
    // timestamp timestamp,
    // status string,
    // offered_products set<frozen<set<frozen<offered_product>>>>
}
