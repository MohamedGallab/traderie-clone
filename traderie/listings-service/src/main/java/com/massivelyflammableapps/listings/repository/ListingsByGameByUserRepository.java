package com.massivelyflammableapps.listings.repository;

import com.massivelyflammableapps.listings.model.ListingByGameByUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

@Repository
public interface ListingsByGameByUserRepository extends MapIdCassandraRepository<ListingByGameByUser> {

    ListingByGameByUser findByUserIdAndGameIdAndBuyingAndListingId(UUID userId, UUID gameId, boolean buying,
            UUID listingId);

    List<ListingByGameByUser> findByUserIdAndGameIdAndBuying(UUID userId, UUID gameId, boolean buying);
}
