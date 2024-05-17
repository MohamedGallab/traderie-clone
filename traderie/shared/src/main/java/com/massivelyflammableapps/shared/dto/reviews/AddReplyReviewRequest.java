package com.massivelyflammableapps.shared.dto.reviews;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddReplyReviewRequest {
    @NonNull
    private EditRequestDto review;
}
