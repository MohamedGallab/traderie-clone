package com.massivelyflammableapps.shared.dto.reviews;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetReviewByReceiverRequest {
    @NonNull
    private UUID receiverId;
}
