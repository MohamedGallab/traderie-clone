package com.massivelyflammableapps.listings.model;

import com.massivelyflammableapps.listings.resources.STATE;
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
@Table
public class ListingByGameByProduct {
        @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
        @NonNull
        private final UUID productId;

        @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
        @NonNull
        private boolean buying;

        @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
        @NonNull
        private final UUID gameId;

        @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
        @NonNull
        private final UUID listingId = UUID.randomUUID();

        @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
        @NonNull
        private final String timestamp = new Date().toString();

        @NonNull
        private String productName;

        @NonNull
        private String productIcon;

        @NonNull
        private int quantity;

        @NonNull
        private UUID userId;

        @NonNull
        @Frozen
        private List<@Frozen List<ProductAmount>> desiredOffer;
        @NonNull
        @Setter
        private STATE state = STATE.ACTIVE;


}
