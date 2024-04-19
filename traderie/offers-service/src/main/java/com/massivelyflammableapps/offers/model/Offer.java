package com.massivelyflammableapps.offers.model;

import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@AllArgsConstructor
@Table
public class Offer {
    @PrimaryKey
    private UUID id;
    private UUID listingId;
    private UUID buyerId;
    private UUID sellerId;
    private String timestamp;
    private String status;
    
    private List<OfferedProduct> offeredProducts;
}
