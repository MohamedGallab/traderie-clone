package com.massivelyflammableapps.listings.model;

import lombok.*;

import java.util.Date;
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
public class ListingByProduct {
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private UUID productId= UUID.randomUUID();

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private boolean listingType;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private UUID listingId;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private String timestamp = new Date().toString();

    @NonNull
    private String productName ;

    @NonNull
    private String productIcon ;

    @NonNull
    private int quantity ;

    @NonNull
    private UUID userId ;

    @NonNull
    @Frozen
    private List<@Frozen List<ProductAmount>> desiredOffer;


}
