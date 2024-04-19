package com.massivelyflammableapps.offers.model;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@AllArgsConstructor
@Table
public class Offer {
    @PrimaryKey
    final private UUID id= UUID.randomUUID();
    private UUID listingId;
    private UUID buyerId;
    private UUID sellerId;
    final private String timestamp = new Date().toString();
    private String status;
    
    private List<OfferedProduct> offeredProducts;
}
