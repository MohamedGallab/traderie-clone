package com.example.review_service.ReviewRepository;

import com.example.review_service.Review.ReviewBySenderAndReceiver;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewBySenderAndReceiverRepository extends MapIdCassandraRepository<ReviewBySenderAndReceiver> {
    Optional<ReviewBySenderAndReceiver> findBySenderIdAndReceiverId(UUID senderId, UUID receiverId);
}
