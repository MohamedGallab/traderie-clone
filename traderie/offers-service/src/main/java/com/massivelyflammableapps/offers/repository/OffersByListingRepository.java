package com.massivelyflammableapps.offers.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

import com.massivelyflammableapps.offers.model.OfferByListing;

@Repository
public interface OffersByListingRepository extends MapIdCassandraRepository<OfferByListing>{
    List<OfferByListing> findByListingId(UUID listingId);
}
