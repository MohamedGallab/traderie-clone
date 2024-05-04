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
@AllArgsConstructor
@RequiredArgsConstructor
@Table
public class ListingByUserByGame {
        @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
        @NonNull
        private final UUID userId;

        @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
        @NonNull
        private final UUID gameId;

        @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
        @NonNull
        private boolean buying;

        @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
        @NonNull
        private final String timestamp = new Date().toString();

        @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
        @NonNull
        private final UUID listingId;

        @NonNull
        private String productName;

        @NonNull
        private String productIcon;

        @NonNull
        private int quantity;

        @NonNull
        @Frozen
        private List<@Frozen List<ProductAmount>> desiredOffer;

        @NonNull
        private STATE state = STATE.ACTIVE;

}
