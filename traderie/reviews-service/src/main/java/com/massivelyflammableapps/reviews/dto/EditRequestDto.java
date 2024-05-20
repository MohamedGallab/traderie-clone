package com.massivelyflammableapps.reviews.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditRequestDto {

    @NotNull(message = "Offer Id shouldn't be null")
    private UUID offerId;

    @NotNull(message = "Sender Id shouldn't be null")
    private UUID senderId;

    @NotNull(message = "Receiver Id shouldn't be null")
    private UUID receiverId;

    @Builder.Default
    private String timestamp = new Date().toString();

    @NotNull(message = "Rating shouldn't be null")
    private int rating;

    @NotNull(message = "Comment shouldn't be null")
    @NotEmpty(message = "Comment shouldn't be empty")
    private String comment;

    @NotNull(message = "Reply shouldn't be null")
    private String reply;

    @NotNull(message = "Is Sender?")
    private boolean IsSender;
}