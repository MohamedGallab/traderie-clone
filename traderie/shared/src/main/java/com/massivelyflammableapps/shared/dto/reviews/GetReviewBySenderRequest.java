package com.massivelyflammableapps.shared.dto.reviews;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetReviewBySenderRequest {
    @NonNull
    private UUID senderId;
}
