package com.massivelyflammableapps.shared.dto.offers;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOffersBySellerRequest {
    @NonNull
    private UUID sellerId;
}
