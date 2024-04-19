package com.massivelyflammableapps.offers.model;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@UserDefinedType("offered_product")
public class OfferedProduct {
    @PrimaryKey
    private UUID id;
    private UUID gameId;
    private UUID productId;
    private int quantity;
    private String productName;
    private String productIcon;
}
