package com.massivelyflammableapps.offers.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

import com.massivelyflammableapps.offers.model.OfferByBuyer;

@Repository
public interface OffersByBuyerRepository extends MapIdCassandraRepository<OfferByBuyer>{
    List<OfferByBuyer> findByBuyerId(UUID buyerId);
}