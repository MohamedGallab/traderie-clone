package com.massivelyflammableapps.shared.dto.listings;

import lombok.Getter;

import java.util.UUID;

@Getter
public class GetListingsByGameByUserDTO extends TokenWrapper{
    private UUID userId;
    private UUID gameId;
    private boolean buying;
    private boolean history;
}
