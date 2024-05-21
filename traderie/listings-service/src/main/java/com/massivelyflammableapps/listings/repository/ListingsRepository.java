package com.massivelyflammableapps.listings.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.massivelyflammableapps.listings.model.Listing;

@Repository
public interface ListingsRepository extends CassandraRepository<Listing, UUID> {

    Listing findByListingId(UUID listingId);
}