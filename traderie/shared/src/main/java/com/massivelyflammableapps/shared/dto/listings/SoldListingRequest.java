package com.massivelyflammableapps.shared.dto.listings;

import java.util.UUID;

import com.massivelyflammableapps.shared.resources.STATE;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoldListingRequest {
    @NonNull
    UUID listingId;
    @NonNull
    STATE state;
}
