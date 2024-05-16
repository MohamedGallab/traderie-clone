package com.example.review_service.Commands;

import com.example.review_service.ReviewService.ReviewService;
import com.example.review_service.dto.EditRequestDto;

import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class EditReviewCommand extends AbstractCommand{
    private ReviewService reviewService;
    @NonNull
    private EditRequestDto editRequestDto;

    @Override
    public Object execute() {
        return reviewService.editReview(editRequestDto);
    }
}
