package com.massivelyflammableapps.offers;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OffersRepository extends CrudRepository<Offer, UUID> {

    //get offers by listing id
    List<Offer> findByListingId(String listingId);
}
