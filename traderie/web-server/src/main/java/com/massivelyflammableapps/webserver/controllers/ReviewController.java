package com.massivelyflammableapps.webserver.controllers;

import com.massivelyflammableapps.shared.dto.reviews.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    @Value("${reviews-service.queue.name}")
    private String queueName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)

    public ResponseEntity<Object> createReview(
           @RequestBody ReviewRequestDto reviewRequestDto) {
        try {
            CreateReviewRequest command = new CreateReviewRequest(reviewRequestDto);
            Object newReview = rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
                    new ParameterizedTypeReference<Object>() {
                    });
            return ResponseEntity.ok(newReview);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


    @GetMapping("/sender")
    public ResponseEntity<List<ReviewRequestDto>> getReviewBySenderId(@RequestParam UUID senderId
    ) {
            try {
                GetReviewBySenderRequest command = new GetReviewBySenderRequest(senderId);
                List<ReviewRequestDto> reviews = rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
                        new ParameterizedTypeReference<List<ReviewRequestDto>>() {
                        });
                return ResponseEntity.ok(reviews);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).build();
            }
    }

    @GetMapping("/receiver")
    public ResponseEntity<List<ReviewRequestDto>> getReviewByReceiverId(@RequestParam UUID receiverId
    ) {
        try {
            GetReviewByReceiverRequest command = new GetReviewByReceiverRequest(receiverId);
            List<ReviewRequestDto> reviews = rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
                    new ParameterizedTypeReference<List<ReviewRequestDto>>() {
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
            AddReplyReviewRequest command = new AddReplyReviewRequest(reviewReq);
            Object reviews = rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
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
            EditReviewRequest command = new EditReviewRequest(reviewReq);
            Object reviews = rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
                    new ParameterizedTypeReference<Object>() {
                    });
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}





