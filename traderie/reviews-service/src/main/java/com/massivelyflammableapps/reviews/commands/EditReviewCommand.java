package com.massivelyflammableapps.reviews.commands;

import com.massivelyflammableapps.reviews.reviewService.ReviewService;
import com.massivelyflammableapps.shared.dto.reviews.EditRequestDto;
import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@NoArgsConstructor
public class EditReviewCommand extends AbstractCommand<Object>{
    @NonNull
    private ReviewService reviewService;
    @NonNull
    private EditRequestDto editRequestDto;

    @Override
    public Object execute() {
        return reviewService.editReview(editRequestDto);
    }
}
