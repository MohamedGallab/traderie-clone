package com.massivelyflammableapps.reviews.reviewRepository;

import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.stereotype.Repository;

import com.massivelyflammableapps.reviews.model.ReviewByReceiver;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewByReceiverRepository extends MapIdCassandraRepository<ReviewByReceiver> {
    List<ReviewByReceiver> findByReceiverId(UUID receiverId);
}
