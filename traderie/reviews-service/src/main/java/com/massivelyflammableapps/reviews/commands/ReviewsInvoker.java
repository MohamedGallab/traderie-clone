package com.massivelyflammableapps.reviews.commands;
import com.massivelyflammableapps.reviews.reviewService.ReviewService;
import com.massivelyflammableapps.shared.dto.reviews.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = {"${service.queue.name}"})
public class ReviewsInvoker {
    @Autowired
    private ReviewService reviewService;

    @RabbitHandler
    public Object createReview(@Payload CreateReviewRequest request) {
        CreateReviewCommand command = new CreateReviewCommand(reviewService, request.getReview());
        return command.execute();
    }

    @RabbitHandler
    public Object getReviewBySender(@Payload GetReviewBySenderRequest request) {
        GetReviewsBySenderCommand command = new GetReviewsBySenderCommand(reviewService, request.getSenderId());
        return command.execute();
    }

    @RabbitHandler
    public Object getReviewByReceiver(@Payload GetReviewByReceiverRequest request) {
        GetReviewsByReceiverCommand command = new GetReviewsByReceiverCommand(reviewService, request.getReceiverId());
        return command.execute();
    }

    @RabbitHandler
    public Object editReview(@Payload EditReviewRequest request) {
        EditReviewCommand command = new EditReviewCommand(reviewService,request.getReview());
        return command.execute();
    }

    @RabbitHandler
    public Object addReply(@Payload AddReplyReviewRequest request){
        EditReviewCommand command = new EditReviewCommand(reviewService,request.getReview());
        return command.execute();
    }

}
//    @RabbitListener(queues = {"hello"})
//    public <T> T excute(AbstractCommand command) {
//        command.setReviewService(reviewService);
//        return command.execute();
//    }
//}