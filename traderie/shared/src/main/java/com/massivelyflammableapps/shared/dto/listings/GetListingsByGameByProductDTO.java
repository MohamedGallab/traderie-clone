package com.massivelyflammableapps.shared.dto.listings;

import lombok.Getter;

import java.util.UUID;

@Getter
public class GetListingsByGameByProductDTO {
    private UUID gameId;
    private UUID productId;
    private boolean buying;
}