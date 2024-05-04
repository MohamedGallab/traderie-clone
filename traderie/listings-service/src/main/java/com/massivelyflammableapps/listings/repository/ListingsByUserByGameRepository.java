package com.massivelyflammableapps.listings.repository;

import com.massivelyflammableapps.listings.model.ListingByUserByGame;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

@Repository
public interface ListingsByUserByGameRepository extends MapIdCassandraRepository<ListingByUserByGame>{

    ListingByUserByGame findByUserIdAndGameIdAndBuyingAndTimestampAndListingId (UUID userId, UUID gameId, boolean buying, String timestamp, UUID listingId);

    List<ListingByUserByGame> findByUserIdAndGameIdAndBuying(UUID userId, UUID gameId, boolean buying);


}



