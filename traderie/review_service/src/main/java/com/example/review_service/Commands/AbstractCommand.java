package com.example.review_service.Commands;

import com.example.review_service.ReviewService.ReviewService;
import lombok.Data;

@Data
public abstract class AbstractCommand<T> {
    private ReviewService reviewService;
    public abstract T execute();
}
