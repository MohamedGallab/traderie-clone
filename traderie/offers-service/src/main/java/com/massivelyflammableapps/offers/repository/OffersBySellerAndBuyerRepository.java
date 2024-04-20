package com.massivelyflammableapps.offers.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

import com.massivelyflammableapps.offers.model.OfferBySellerAndBuyer;

@Repository
public interface OffersBySellerAndBuyerRepository extends MapIdCassandraRepository<OfferBySellerAndBuyer>{
    List<OfferBySellerAndBuyer> findBySellerIdAndBuyerId(UUID sellerId,UUID buyerId);
}
