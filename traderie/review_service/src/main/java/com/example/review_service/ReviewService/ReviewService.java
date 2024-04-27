package com.example.review_service.ReviewService;

import com.example.review_service.Responses.ResponseMessage;
import com.example.review_service.Review.ReviewByReceiver;
import com.example.review_service.Review.ReviewBySender;
import com.example.review_service.Review.ReviewBySenderAndReceiver;
import com.example.review_service.ReviewRepository.ReviewByReceiverRepository;
import com.example.review_service.ReviewRepository.ReviewBySenderAndReceiverRepository;
import com.example.review_service.ReviewRepository.ReviewBySenderRepository;
import com.example.review_service.Validators.ObjectsValidator;
import com.example.review_service.dto.EditRequestDto;
import com.example.review_service.dto.ReviewRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    @Autowired
    private ReviewBySenderRepository reviewBySenderRepository;
    @Autowired
    private ReviewByReceiverRepository reviewByReceiverRepository;
    @Autowired
    private ReviewBySenderAndReceiverRepository reviewBySenderAndReceiverRepository;

    private final ObjectsValidator<ReviewRequestDto> reviewRequestDtoObjectsValidator;

    private final ObjectsValidator<EditRequestDto> editRequestDtoObjectsValidator;


    public Object createReview(ReviewRequestDto review) {
        var violations  = reviewRequestDtoObjectsValidator.validate(review);
        if(!violations.isEmpty()){
            ResponseMessage responseMessage = new ResponseMessage();
            responseMessage.setMessage(String.join("|",violations));
            responseMessage.setStatus("400");
            return responseMessage;
        }
        Optional<ReviewBySenderAndReceiver> review1 = reviewBySenderAndReceiverRepository.findBySenderIdAndReceiverId(review.getSenderId(),review.getReceiverId());
        if(review1.isPresent()){
            return new ResponseMessage("You have already made a review to this user","500");
        }
        if(review.getSenderId().equals(review.getReceiverId())){
            return new ResponseMessage("You cannot review yourself :)","500");
        }
        ReviewBySender newReviewBySender = new ReviewBySender(
                review.getOfferId(),
                review.getSenderId(),
                review.getReceiverId(),
                review.getTimestamp(),
                review.getRating(),
                review.getComment(),
                review.getReply()
        );

        ReviewByReceiver newReviewByReceiver = new ReviewByReceiver(
                review.getOfferId(),
                review.getSenderId(),
                review.getReceiverId(),
                review.getTimestamp(),
                review.getRating(),
                review.getComment(),
                review.getReply()
        );

        ReviewBySenderAndReceiver newReviewBySenderAndReceiver = new ReviewBySenderAndReceiver(
                review.getOfferId(),
                review.getSenderId(),
                review.getReceiverId(),
                review.getTimestamp(),
                review.getRating(),
                review.getComment(),
                review.getReply()
        );
        ReviewBySender result = reviewBySenderRepository.save(newReviewBySender);
        reviewByReceiverRepository.save(newReviewByReceiver);
        reviewBySenderAndReceiverRepository.save(newReviewBySenderAndReceiver);

        return result;
    }

    public List<ReviewBySender> getReviewBySender(UUID senderId) {
        return reviewBySenderRepository.findBySenderId(senderId);
    }
    public List<ReviewByReceiver> getReviewByReceiver(UUID receiverId) {
        return reviewByReceiverRepository.findByReceiverId(receiverId);
    }

    public Object editReview(EditRequestDto review){
        var violations  = editRequestDtoObjectsValidator.validate(review);
        if(!violations.isEmpty()){
            ResponseMessage responseMessage = new ResponseMessage();
            responseMessage.setMessage(String.join("|",violations));
            responseMessage.setStatus("400");
            return responseMessage;
        }
        Optional<ReviewBySenderAndReceiver> review1 = reviewBySenderAndReceiverRepository.findBySenderIdAndReceiverId(review.getSenderId(),review.getReceiverId());
        if(review1.isEmpty()){
            return new ResponseMessage("Review not found, Try Again","404");
        }
        if(!review1.get().getOfferId().equals(review.getOfferId())){
            return new ResponseMessage("InValid Offer Id","500");
        }
        System.out.println(review1.get().getOfferId());
        ReviewBySender newReviewBySender = new ReviewBySender(
                review.getOfferId(),
                review.getSenderId(),
                review.getReceiverId(),
                review.getTimestamp(),
                review.getRating(),
                review.getComment(),
                review.getReply()
        );

        ReviewByReceiver newReviewByReceiver = new ReviewByReceiver(
                review.getOfferId(),
                review.getSenderId(),
                review.getReceiverId(),
                review.getTimestamp(),
                review.getRating(),
                review.getComment(),
                review.getReply()
        );

        ReviewBySenderAndReceiver newReviewBySenderAndReceiver = new ReviewBySenderAndReceiver(
                review.getOfferId(),
                review.getSenderId(),
                review.getReceiverId(),
                review.getTimestamp(),
                review.getRating(),
                review.getComment(),
                review.getReply()
        );
        ReviewBySender result = reviewBySenderRepository.save(newReviewBySender);
        reviewByReceiverRepository.save(newReviewByReceiver);
        reviewBySenderAndReceiverRepository.save(newReviewBySenderAndReceiver);

        return result;
    }

}
