package com.massivelyflammableapps.reviews.reviewRepository;

import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.stereotype.Repository;

import com.massivelyflammableapps.reviews.model.ReviewBySenderAndReceiver;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewBySenderAndReceiverRepository extends MapIdCassandraRepository<ReviewBySenderAndReceiver> {
    Optional<ReviewBySenderAndReceiver> findBySenderIdAndReceiverId(UUID senderId, UUID receiverId);
}
