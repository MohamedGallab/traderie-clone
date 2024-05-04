package com.massivelyflammableapps.listings.dto;

import com.massivelyflammableapps.listings.model.ProductAmount;
import com.massivelyflammableapps.listings.resources.STATE;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
public class ListingUpdateDTO extends TokenWrapper{
    private UUID listingId ;

    private String timestamp;

    private UUID productId;

    private UUID gameId;

    private boolean buying;

    private STATE state;

    UUID userId;
}
