package com.massivelyflammableapps.listings.repository;

import com.massivelyflammableapps.listings.model.ListingByGameByUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

@Repository
<<<<<<< HEAD:traderie/listings-service/src/main/java/com/massivelyflammableapps/listings/repository/ListingsByUserByGameRepository.java
public interface ListingsByUserByGameRepository extends MapIdCassandraRepository<ListingByUserByGame>{
<<<<<<< HEAD
=======
public interface ListingsByGameByUserRepository extends MapIdCassandraRepository<ListingByGameByUser>{
>>>>>>> 852804a (Added commands):traderie/listings-service/src/main/java/com/massivelyflammableapps/listings/repository/ListingsByGameByUserRepository.java

    ListingByGameByUser findByUserIdAndGameIdAndBuyingAndTimestampAndListingId (UUID userId, UUID gameId, boolean buying, String timestamp, UUID listingId);

    List<ListingByGameByUser> findByUserIdAndGameIdAndBuying(UUID userId, UUID gameId, boolean buying);


=======
    List<ListingByUserByGame> findByUserIdAndGameIdAndListingType(UUID userId, UUID gameId, boolean listingType);
>>>>>>> 07852ab (Modified models and added repositories)
}



