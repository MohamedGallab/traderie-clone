package com.massivelyflammableapps.listings.model;

import com.massivelyflammableapps.listings.resources.STATE;
import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table
public class ListingByGameByUser {
        @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
        @NonNull
        private UUID userId;

        @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
        @NonNull
        private UUID gameId;

        @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
        @NonNull
        private boolean buying;

        @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
        @NonNull
        private String timestamp = new Date().toString();

        @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
        @NonNull
        private UUID listingId;

        @NonNull
        private String productName ;

        @NonNull
        private String productIcon ;

        @NonNull
        private int quantity ;

        @NonNull
        @Frozen
        private List<@Frozen List<ProductAmount>> desiredOffer;

        @NonNull
        private STATE state = STATE.ACTIVE;
}
