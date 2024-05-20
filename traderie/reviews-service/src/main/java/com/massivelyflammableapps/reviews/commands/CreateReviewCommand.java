package com.massivelyflammableapps.reviews.commands;
import com.massivelyflammableapps.reviews.reviewService.ReviewService;
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
