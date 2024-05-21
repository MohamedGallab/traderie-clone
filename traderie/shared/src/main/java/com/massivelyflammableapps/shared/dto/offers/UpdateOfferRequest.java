package com.massivelyflammableapps.shared.dto.offers;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOfferRequest implements Serializable {
    @NonNull
    private UUID offerId;
    @NonNull
    private String status;
}