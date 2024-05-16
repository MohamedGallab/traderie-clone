package com.example.review_service.Review;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

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
public class ReviewBySenderAndReceiver {

    @NonNull
    private UUID offerId;

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private UUID senderId;

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private UUID receiverId;

    private String timestamp;

    @NotNull
    private int rating;

    @NotNull
    @NotEmpty
    private String comment;
    private String reply;

}
