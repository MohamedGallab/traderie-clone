package com.example.review_service.Review;

import java.util.UUID;

import com.massivelyflammableapps.shared.dto.reviews.ReviewRequestDto;
import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class ReviewBySender {

    @NonNull
    private UUID offerId;

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private UUID senderId;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private UUID receiverId;

    private String timestamp;

    @NotNull
    private int rating;

    @NotNull
    @NotEmpty
    private String comment;
    private String reply;

    public ReviewRequestDto toDTO(){
        return new ReviewRequestDto(offerId,senderId,receiverId, timestamp, rating, reply,comment);
}
}
