package com.massivelyflammableapps.listings.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.UUID;

@Data
@AllArgsConstructor
@UserDefinedType("offered_product")
public class Product_amount {
    private int amount;
    private int game_id;
    private int item_id;
    private String item_name;
    private String item_img;
}
