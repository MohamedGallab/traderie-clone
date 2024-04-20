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
public class Listing_by_product {
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private UUID product_id= UUID.randomUUID();

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private boolean listing_type;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private UUID listing_id;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private String time_stamp = new Date().toString();

    @NonNull
    private String product_name ;

    @NonNull
    private String product_icon ;

    @NonNull
    private int quantity ;

    @NonNull
    private UUID user_id ;

    @NonNull
    @Frozen
    private List<@Frozen List<Product_amount>> desired_offer;


}
