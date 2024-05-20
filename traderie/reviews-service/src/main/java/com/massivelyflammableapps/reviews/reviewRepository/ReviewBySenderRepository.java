package com.massivelyflammableapps.reviews.reviewRepository;

import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.stereotype.Repository;

import com.massivelyflammableapps.reviews.model.ReviewBySender;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewBySenderRepository extends MapIdCassandraRepository<ReviewBySender> {
    List<ReviewBySender> findBySenderId(UUID senderId);
}
