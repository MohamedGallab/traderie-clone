package com.massivelyflammableapps.listings.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

import com.massivelyflammableapps.listings.model.ListingByGameByProduct;

@Repository
public interface ListingsByGameByProductRepository extends MapIdCassandraRepository<ListingByGameByProduct> {
    ListingByGameByProduct findByGameIdAndProductIdAndBuyingAndListingId(UUID gameId, UUID productId,
            boolean buying, UUID listingId);

    List<ListingByGameByProduct> findByGameIdAndProductIdAndBuying(UUID gameId, UUID productId, boolean buying);

}
