package com.massivelyflammableapps.offers.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.massivelyflammableapps.offers.model.Offer;

public interface OffersRepository extends CrudRepository<Offer, UUID> {
    Offer findByListingId(String listingId);
}