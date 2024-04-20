package com.massivelyflammableapps.offers.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

import com.massivelyflammableapps.offers.model.OfferBySeller;

@Repository
public interface OffersBySellerRepository extends MapIdCassandraRepository<OfferBySeller>{
    List<OfferBySeller> findBySellerId(UUID sellerId);
}
