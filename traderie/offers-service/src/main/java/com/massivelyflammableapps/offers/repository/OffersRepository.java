package com.massivelyflammableapps.offers.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.massivelyflammableapps.offers.model.Offer;

@Repository
public interface OffersRepository extends CassandraRepository<Offer, UUID> {
    @AllowFiltering
    List<Offer> findByListingId(UUID listingId);
}