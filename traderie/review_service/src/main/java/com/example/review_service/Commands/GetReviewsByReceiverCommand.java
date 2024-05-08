package com.example.review_service.Commands;

import com.example.review_service.Review.ReviewByReceiver;
import com.example.review_service.ReviewService.ReviewService;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class GetReviewsByReceiverCommand extends AbstractCommand{
    private ReviewService reviewService;
    @NonNull
    private UUID receiverId;

    @Override
    public List<ReviewByReceiver> execute() {
        return reviewService.getReviewByReceiver(receiverId);
    }
}
