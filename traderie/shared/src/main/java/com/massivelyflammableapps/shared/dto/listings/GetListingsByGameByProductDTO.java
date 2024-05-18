package com.massivelyflammableapps.shared.dto.listings;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@Getter
@ToString
public class GetListingsByGameByProductDTO {
    private UUID gameId;
    private UUID productId;
    private boolean buying;
}
