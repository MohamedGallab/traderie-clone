package com.massivelyflammableapps.shared.dto.listings;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@Setter
public class GetMyListingsByGameDTO {
    private UUID userId;
    private UUID gameId;
    private boolean buying;
    private boolean history;
}
