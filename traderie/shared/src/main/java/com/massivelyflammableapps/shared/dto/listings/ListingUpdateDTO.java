package com.massivelyflammableapps.shared.dto.listings;

import com.massivelyflammableapps.resources.STATE;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ListingUpdateDTO extends TokenWrapper{
    private UUID listingId ;

    private String timestamp;

    private UUID productId;

    private UUID gameId;

    private boolean buying;

    private STATE state;

    UUID userId;
}
