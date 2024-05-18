package com.massivelyflammableapps.shared.dto.listings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAmountDTO implements Serializable{
    private int amount;
    private int gameId;
    private int itemId;
    private String itemName;
    private String itemImg;


}
