package com.massivelyflammableapps.offers.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.massivelyflammableapps.offers.model.Offer;

@Repository
public interface OffersRepository extends CassandraRepository<Offer, UUID> {
}