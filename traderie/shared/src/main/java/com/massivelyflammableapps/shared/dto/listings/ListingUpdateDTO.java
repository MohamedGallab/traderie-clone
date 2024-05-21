package com.massivelyflammableapps.shared.dto.listings;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

import com.massivelyflammableapps.shared.resources.STATE;

@Getter
@ToString
public class ListingUpdateDTO {
    private UUID listingId;
    private String timestamp;
    private UUID productId;
    private UUID gameId;
    private boolean buying;
    private STATE state;
}
