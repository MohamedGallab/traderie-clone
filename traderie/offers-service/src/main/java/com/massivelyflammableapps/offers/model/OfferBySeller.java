package com.massivelyflammableapps.offers.model;

import lombok.*;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Table
public class OfferBySeller {
    
    @PrimaryKeyColumn( type = PrimaryKeyType.CLUSTERED) 
    private UUID id;

    @NonNull
    private UUID listingId; 
    
    @NonNull
    private UUID buyerId;

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private UUID sellerId;

    @NonNull
    private String timestamp;

    @NonNull
    private String status;

    @NonNull
    @Frozen
    private List<List<OfferedProduct>> offeredProducts;
}
