package com.example.review_service.Commands;

import com.example.review_service.ReviewService.ReviewService;

import com.massivelyflammableapps.shared.dto.reviews.EditRequestDto;
import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class EditReviewCommand extends AbstractCommand{
    @NonNull
    private ReviewService reviewService;
    @NonNull
    private EditRequestDto editRequestDto;

    @Override
    public Object execute() {
        return reviewService.editReview(editRequestDto);
    }
}
