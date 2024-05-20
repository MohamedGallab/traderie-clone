package com.massivelyflammableapps.reviews.reviewService;

import com.massivelyflammableapps.reviews.model.ReviewByReceiver;
import com.massivelyflammableapps.reviews.model.ReviewBySender;
import com.massivelyflammableapps.reviews.model.ReviewBySenderAndReceiver;
import com.massivelyflammableapps.reviews.responses.ResponseMessage;
import com.massivelyflammableapps.reviews.reviewRepository.ReviewByReceiverRepository;
import com.massivelyflammableapps.reviews.reviewRepository.ReviewBySenderAndReceiverRepository;
import com.massivelyflammableapps.reviews.reviewRepository.ReviewBySenderRepository;
import com.massivelyflammableapps.reviews.validators.ObjectsValidator;
import com.massivelyflammableapps.shared.dto.reviews.EditRequestDto;
import com.massivelyflammableapps.shared.dto.reviews.ReviewRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @CacheEvict(value = "reviewsCache", key = "#review.senderId")
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

    @Cacheable("reviewsCache")
    public List<ReviewRequestDto> getReviewBySender(UUID senderId) {
        var reviews =reviewBySenderRepository.findBySenderId(senderId);
        List<ReviewRequestDto> reviewRequestDtos = new ArrayList<>();
        for (ReviewBySender review : reviews) {
            reviewRequestDtos.add(review.toDTO());
        }
        return reviewRequestDtos;
    }
    @Cacheable("reviewsCache")
    public List<ReviewRequestDto> getReviewByReceiver(UUID receiverId) {
        var reviews =reviewByReceiverRepository.findByReceiverId(receiverId);
        List<ReviewRequestDto> reviewRequestDtos = new ArrayList<>();
        for (ReviewByReceiver review : reviews) {
            reviewRequestDtos.add(review.toDTO());
        }
        return  reviewRequestDtos;
    }

    @CacheEvict(value = "reviewsCache", key = "#review.senderId")
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
