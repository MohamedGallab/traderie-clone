package com.example.review_service.Commands;
import com.example.review_service.ReviewService.ReviewService;
import com.massivelyflammableapps.shared.dto.reviews.ReviewRequestDto;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@NoArgsConstructor
public class CreateReviewCommand extends AbstractCommand<Object>{
    @NonNull
    private ReviewService reviewService;

    @NonNull
    private ReviewRequestDto reviewRequestDto;

    @Override
    public Object execute() {
        return reviewService.createReview(reviewRequestDto);
    }
}
