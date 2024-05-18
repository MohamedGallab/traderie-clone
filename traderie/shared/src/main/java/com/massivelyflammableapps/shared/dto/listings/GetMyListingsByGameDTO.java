package com.massivelyflammableapps.shared.dto.listings;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString

public class GetMyListingsByGameDTO extends TokenWrapper {
    private UUID userId;
    private UUID gameId;
    private boolean buying;
    private boolean history;
}
