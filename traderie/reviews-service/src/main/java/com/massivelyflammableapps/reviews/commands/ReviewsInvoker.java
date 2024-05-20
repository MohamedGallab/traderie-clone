package com.massivelyflammableapps.reviews.commands;

import com.massivelyflammableapps.reviews.reviewService.ReviewService;
import com.massivelyflammableapps.shared.CommandHandler;
import com.massivelyflammableapps.shared.dto.admin.AddCommandRequest;
import com.massivelyflammableapps.shared.dto.admin.DeleteCommandRequest;
import com.massivelyflammableapps.shared.dto.admin.ExecuteCommandRequest;
import com.massivelyflammableapps.shared.dto.admin.UpdateCommandRequest;
import com.massivelyflammableapps.shared.dto.reviews.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.ArrayList;

@Service
@RabbitListener(queues = {"${service.queue.name}"})
public class ReviewsInvoker {
    @Autowired
    private ReviewService reviewService;

    private CommandHandler commandHandler = new CommandHandler();

    @Async
    @RabbitHandler
    public CompletableFuture<Object> createReview(@Payload CreateReviewRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                CreateReviewCommand command = new CreateReviewCommand(reviewService, request.getReview());
                return command.execute();
            } catch (Exception e) {
                return new Object();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Object> getReviewBySender(@Payload GetReviewBySenderRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                GetReviewsBySenderCommand command = new GetReviewsBySenderCommand(reviewService, request.getSenderId());
                return command.execute();
            } catch (Exception e) {
                return new ArrayList<>();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Object> getReviewByReceiver(@Payload GetReviewByReceiverRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                GetReviewsByReceiverCommand command = new GetReviewsByReceiverCommand(reviewService, request.getReceiverId());
                return command.execute();
            } catch (Exception e) {
                return new ArrayList<>();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Object> editReview(@Payload EditReviewRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                EditReviewCommand command = new EditReviewCommand(reviewService, request.getReview());
                return command.execute();
            } catch (Exception e) {
                return new Object();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Object> addReply(@Payload AddReplyReviewRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                EditReviewCommand command = new EditReviewCommand(reviewService, request.getReview());
                return command.execute();
            } catch (Exception e) {
                return new Object();
            }
        });
    }

    
    @Async
    @RabbitHandler
    public CompletableFuture<Boolean> addCommand(@Payload AddCommandRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return commandHandler.createCommandFile(request.getCommandClass(), request.getCommandCode());
            } catch (Exception e) {
                return false;
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Boolean> deleteCommand(@Payload DeleteCommandRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return commandHandler.deleteCommandFile(request.getCommandClass());
            } catch (Exception e) {
                return false;
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Boolean> updateCommand(@Payload UpdateCommandRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                boolean deleteResult = commandHandler.deleteCommandFile(request.getCommandClass());
                if (!deleteResult) {
                    return false;
                }
                return commandHandler.createCommandFile(request.getCommandClass(), request.getCommandCode());
            } catch (Exception e) {
                return false;
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Object> executeCommand(@Payload ExecuteCommandRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Object result = commandHandler.runIt(request.getCommandClass(), request.getParamsObj());
                if (result == null) {
                    return "void";
                }
                return result;
            } catch (Exception e) {
                return false;
            }
        });
    }
}
