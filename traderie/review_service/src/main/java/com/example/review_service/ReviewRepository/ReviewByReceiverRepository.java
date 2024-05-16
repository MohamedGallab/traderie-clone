package com.example.review_service.ReviewRepository;

import com.example.review_service.Review.ReviewByReceiver;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewByReceiverRepository extends MapIdCassandraRepository<ReviewByReceiver> {
    List<ReviewByReceiver> findByReceiverId(UUID receiverId);
}
