package com.massivelyflammableapps.listings.dto;

import com.massivelyflammableapps.listings.model.ProductAmount;
import com.massivelyflammableapps.listings.resources.STATE;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
public class CreateListingDTO extends TokenWrapper{
    private UUID productId;
    private boolean buying;
    private String productName ;
    private String productIcon ;
    private int quantity ;
    @Frozen
    private List<@Frozen List<ProductAmount>> desiredOffer;
    private UUID gameId;
    private STATE state;
}
