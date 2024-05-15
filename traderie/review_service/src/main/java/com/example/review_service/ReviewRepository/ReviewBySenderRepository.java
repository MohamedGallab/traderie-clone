package com.example.review_service.ReviewRepository;

import com.example.review_service.Review.ReviewBySender;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewBySenderRepository extends MapIdCassandraRepository<ReviewBySender> {
    List<ReviewBySender> findBySenderId(UUID senderId);
}
