package com.example.review_service.ReviewController;


import com.example.review_service.Commands.*;
import com.example.review_service.Review.ReviewByReceiver;
import com.example.review_service.Review.ReviewBySender;
import com.example.review_service.ReviewService.ReviewService;
import com.example.review_service.dto.EditRequestDto;
import com.example.review_service.dto.ReviewRequestDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)

    public ResponseEntity<Object> createReview(
           @RequestBody ReviewRequestDto reviewRequestDto) {
        try {
            System.out.println(reviewRequestDto.getSenderId());
            AbstractCommand command = new CreateReviewCommand(reviewRequestDto);
            Object newReview = rabbitTemplate.convertSendAndReceiveAsType("", "hello", command,
                    new ParameterizedTypeReference<Object>() {
                    });
            return ResponseEntity.ok(newReview);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


    @GetMapping("/sender")
    public ResponseEntity<List<ReviewBySender>> getReviewBySenderId(@RequestParam UUID senderId
    ) {
            try {
                AbstractCommand command = new GetReviewsBySenderCommand(senderId);
                List<ReviewBySender> reviews = rabbitTemplate.convertSendAndReceiveAsType("", "hello", command,
                        new ParameterizedTypeReference<List<ReviewBySender>>() {
                        });
                return ResponseEntity.ok(reviews);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).build();
            }
    }

    @GetMapping("/receiver")
    public ResponseEntity<List<ReviewByReceiver>> getReviewByReceiverId(@RequestParam UUID receiverId
    ) {
        try {
            AbstractCommand command = new GetReviewsByReceiverCommand(receiverId);
            List<ReviewByReceiver> reviews = rabbitTemplate.convertSendAndReceiveAsType("", "hello", command,
                    new ParameterizedTypeReference<List<ReviewByReceiver>>() {
                    });
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


    @PutMapping("/reply")
    public ResponseEntity<Object> addReply(@RequestBody EditRequestDto reviewReq) {
        try {
            AbstractCommand command = new EditReviewCommand(reviewReq);
            Object reviews = rabbitTemplate.convertSendAndReceiveAsType("", "hello", command,
                    new ParameterizedTypeReference<Object>() {
                    });
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    @PutMapping("/edit")
    public ResponseEntity<Object> editReview(@RequestBody EditRequestDto reviewReq) {
        try {
            AbstractCommand command = new EditReviewCommand(reviewReq);
            Object reviews = rabbitTemplate.convertSendAndReceiveAsType("", "hello", command,
                    new ParameterizedTypeReference<Object>() {
                    });
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}





