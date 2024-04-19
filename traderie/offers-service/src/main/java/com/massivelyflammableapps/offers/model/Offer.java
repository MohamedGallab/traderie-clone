package com.massivelyflammableapps.offers.model;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Table
public class Offer {
    @PrimaryKey
    private UUID id= UUID.randomUUID();

    @NonNull
    private UUID listingId;

    @NonNull
    private UUID buyerId;

    @NonNull
    private UUID sellerId;

    private String timestamp = new Date().toString();

    @NonNull
    private String status;

    @NonNull
    @Frozen
    private List<List<OfferedProduct>> offeredProducts;
}
