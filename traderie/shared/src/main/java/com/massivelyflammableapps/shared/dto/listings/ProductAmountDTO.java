package com.massivelyflammableapps.shared.dto.listings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAmountDTO implements Serializable {
    private UUID id;
    private int amount;
    private UUID gameId;
    private UUID itemId;
    private String itemName;
    private String itemImg;
}
