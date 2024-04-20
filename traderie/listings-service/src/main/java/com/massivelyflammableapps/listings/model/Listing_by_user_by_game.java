package com.massivelyflammableapps.listings.model;

import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Table
public class Listing_by_user_by_game {
        @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
        private UUID user_id;

        @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
        private UUID game_id;

        @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
        private boolean listing_type;

        @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
        private String time_stamp = new Date().toString();

        @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
        private UUID listing_id;

        @NonNull
        private String product_name ;

        @NonNull
        private String product_icon ;

        @NonNull
        private int quantity ;

        @NonNull
        @Frozen
        private List<@Frozen List<Product_amount>> desired_offer;

}
