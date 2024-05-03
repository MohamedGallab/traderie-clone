package com.massivelyflammableapps.listings.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

import com.massivelyflammableapps.listings.model.ListingByProduct;

@Repository
public interface ListingsByProductRepository extends MapIdCassandraRepository<ListingByProduct>{
    List<ListingByProduct> findByProductIdAndListingType(UUID productId,boolean listingType);
}



