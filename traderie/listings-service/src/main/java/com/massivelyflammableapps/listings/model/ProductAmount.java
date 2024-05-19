package com.massivelyflammableapps.listings.model;

import com.massivelyflammableapps.shared.dto.listings.ProductAmountDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@AllArgsConstructor
@UserDefinedType("product_amount")
@NoArgsConstructor
public class ProductAmount {
    @PrimaryKey
    private UUID id;
    private int amount;
    private UUID gameId;
    private UUID itemId;
    private String itemName;
    private String itemImg;

    public ProductAmount(ProductAmountDTO productAmountDTO) {
        this.id = productAmountDTO.getId();
        this.amount = productAmountDTO.getAmount();
        this.gameId = productAmountDTO.getGameId();
        this.itemId = productAmountDTO.getItemId();
        this.itemName = productAmountDTO.getItemName();
        this.itemImg = productAmountDTO.getItemImg();
    }

    public ProductAmountDTO toDTO() {
        return new ProductAmountDTO(id, amount, gameId, itemId, itemName, itemImg);
    }
}
