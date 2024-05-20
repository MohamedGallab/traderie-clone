package com.massivelyflammableapps.reviews.commands;

import com.massivelyflammableapps.reviews.reviewService.ReviewService;
import com.massivelyflammableapps.shared.dto.reviews.ReviewRequestDto;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@NoArgsConstructor
public class GetReviewsByReceiverCommand extends AbstractCommand<List<ReviewRequestDto>>{
    @NonNull
    private ReviewService reviewService;
    @NonNull
    private UUID receiverId;

    @Override
    public List<ReviewRequestDto> execute() {
        return reviewService.getReviewByReceiver(receiverId);
    }
}
