package com.massivelyflammableapps.shared.dto.offers;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferedProductDTO implements Serializable{
    private UUID id;
    private UUID gameId;
    private UUID productId;
    private int quantity;
    private String productName;
    private String productIcon;
}