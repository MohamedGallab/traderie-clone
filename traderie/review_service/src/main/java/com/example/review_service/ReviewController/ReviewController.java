package com.example.review_service.ReviewController;


import com.example.review_service.Review.ReviewByReceiver;
import com.example.review_service.Review.ReviewBySender;
import com.example.review_service.ReviewService.ReviewService;
import com.example.review_service.dto.EditRequestDto;
import com.example.review_service.dto.ReviewRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
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


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)

    public ResponseEntity<Object> createReview(
           @RequestBody ReviewRequestDto reviewRequestDto) {
        try {
            System.out.println(reviewRequestDto.getSenderId());
            Object newReview = reviewService.createReview(reviewRequestDto);
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
                List<ReviewBySender> reviews = reviewService.getReviewBySender(senderId);
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
            List<ReviewByReceiver> reviews = reviewService.getReviewByReceiver(receiverId);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


    @PutMapping("/reply")
    public ResponseEntity<Object> addReply(@RequestBody EditRequestDto reviewReq) {
        try {
            Object reviews = reviewService.editReview(reviewReq);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    @PutMapping("/edit")
    public ResponseEntity<Object> editReview(@RequestBody EditRequestDto reviewReq) {
        try {
            Object reviews = reviewService.editReview(reviewReq);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}





