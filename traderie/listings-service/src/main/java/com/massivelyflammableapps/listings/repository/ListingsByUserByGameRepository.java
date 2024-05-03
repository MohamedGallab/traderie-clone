package com.massivelyflammableapps.listings.repository;

import com.massivelyflammableapps.listings.model.ListingByUserByGame;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

@Repository
public interface ListingsByUserByGameRepository extends MapIdCassandraRepository<ListingByUserByGame>{
    List<ListingByUserByGame> findByUserIdAndGameIdAndListingType(UUID userId, UUID gameId, boolean listingType);
}



