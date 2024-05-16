package com.example.review_service.Commands;

import com.example.review_service.Review.ReviewBySender;
import com.example.review_service.ReviewService.ReviewService;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class GetReviewsBySenderCommand extends AbstractCommand{
    private ReviewService reviewService;
    @NonNull
    private UUID senderId;

    @Override
    public List<ReviewBySender> execute() {
        return reviewService.getReviewBySender(senderId);
    }
}
