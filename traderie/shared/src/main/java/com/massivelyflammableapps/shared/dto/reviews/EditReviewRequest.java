package com.massivelyflammableapps.shared.dto.reviews;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditReviewRequest {
    @NonNull
    private EditRequestDto review;
}
