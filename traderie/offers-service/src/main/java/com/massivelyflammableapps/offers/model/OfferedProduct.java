package com.massivelyflammableapps.offers.model;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import com.massivelyflammableapps.shared.dto.offers.OfferedProductDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@UserDefinedType("offered_product")
@NoArgsConstructor
public class OfferedProduct {
    @PrimaryKey
    private UUID id;
    private UUID gameId;
    private UUID productId;
    private int quantity;
    private String productName;
    private String productIcon;
    
    public OfferedProduct(OfferedProductDTO offeredProductDTO) {
        this.id = offeredProductDTO.getId();
        this.gameId = offeredProductDTO.getGameId();
        this.productId = offeredProductDTO.getProductId();
        this.quantity = offeredProductDTO.getQuantity();
        this.productName = offeredProductDTO.getProductName();
        this.productIcon = offeredProductDTO.getProductIcon();
    }

    public OfferedProductDTO toDTO() {
        return new OfferedProductDTO(id, gameId, productId, quantity, productName, productIcon);
    }
}
