package com.massivelyflammableapps.offers;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface VetRepository extends CrudRepository<Vet, UUID> {  
  Vet findByFirstName(String username);
}
