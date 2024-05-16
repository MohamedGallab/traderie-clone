package com.massivelyflammableapps.listings.model;

import com.massivelyflammableapps.shared.dto.listings.ProductAmountDTO;
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
    public ProductAmount(ProductAmountDTO productAmountDTO) {
        this.amount = productAmountDTO.getAmount();
        this.gameId = productAmountDTO.getGameId();
        this.itemId = productAmountDTO.getItemId();
        this.itemName = productAmountDTO.getItemName();
        this.itemImg = productAmountDTO.getItemImg();
    }
    public ProductAmountDTO toDTO(){
        return new ProductAmountDTO(amount, gameId, itemId, itemName, itemImg);
    }
}
