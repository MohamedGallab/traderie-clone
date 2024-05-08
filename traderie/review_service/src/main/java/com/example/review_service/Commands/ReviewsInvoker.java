package com.example.review_service.Commands;
import com.example.review_service.ReviewService.ReviewService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewsInvoker {
    @Autowired
    private ReviewService reviewService;

    @RabbitListener(queues = {"hello"})
    public <T> T excute(AbstractCommand command) {
        command.setReviewService(reviewService);
        return command.execute();
    }
}