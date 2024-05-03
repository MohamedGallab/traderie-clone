package com.massivelyflammableapps.listings.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.UUID;

@Data
@AllArgsConstructor
@UserDefinedType("offered_product")
public class ProductAmount {
    private int amount;
    private int gameId;
    private int itemId;
    private String itemName;
    private String itemImg;
}
