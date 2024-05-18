package com.massivelyflammableapps.shared.dto.reviews;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewRequest {
    @NonNull
    private ReviewRequestDto review;
}
