package com.massivelyflammableapps.reviews.commands;

import com.massivelyflammableapps.reviews.reviewService.ReviewService;

import lombok.Data;

@Data
public abstract class AbstractCommand<T> {
    private ReviewService reviewService;
    public abstract T execute();
}
