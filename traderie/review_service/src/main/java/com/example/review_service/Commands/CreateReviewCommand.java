package com.example.review_service.Commands;
import com.example.review_service.ReviewService.ReviewService;
import com.example.review_service.dto.ReviewRequestDto;
import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class CreateReviewCommand extends AbstractCommand{
    private ReviewService reviewService;

    @NonNull
    private ReviewRequestDto reviewRequestDto;

    @Override
    public Object execute() {
        return reviewService.createReview(reviewRequestDto);
    }
}
