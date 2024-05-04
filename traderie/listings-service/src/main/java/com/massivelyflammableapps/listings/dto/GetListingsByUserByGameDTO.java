package com.massivelyflammableapps.listings.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class GetListingsByUserByGameDTO extends TokenWrapper{
    private UUID userId;
    private UUID gameId;
    private boolean buying;
    private boolean history;
}
