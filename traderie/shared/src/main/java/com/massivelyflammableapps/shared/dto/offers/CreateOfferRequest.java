package com.massivelyflammableapps.shared.dto.offers;

import java.io.Serializable;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOfferRequest implements Serializable {
    @NonNull
    private OfferDTO offer;
}
