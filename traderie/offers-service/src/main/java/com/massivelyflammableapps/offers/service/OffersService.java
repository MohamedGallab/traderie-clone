package com.massivelyflammableapps.offers.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.cassandra.core.mapping.BasicMapId;
import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.massivelyflammableapps.offers.model.Offer;
import com.massivelyflammableapps.offers.model.OfferByBuyer;
import com.massivelyflammableapps.offers.model.OfferByListing;
import com.massivelyflammableapps.offers.model.OfferBySeller;
import com.massivelyflammableapps.offers.model.OfferBySellerAndBuyer;
import com.massivelyflammableapps.offers.repository.OffersByBuyerRepository;
import com.massivelyflammableapps.offers.repository.OffersByListingRepository;
import com.massivelyflammableapps.offers.repository.OffersBySellerAndBuyerRepository;
import com.massivelyflammableapps.offers.repository.OffersBySellerRepository;
import com.massivelyflammableapps.offers.repository.OffersRepository;
import com.massivelyflammableapps.shared.dto.listings.MarkListingDTO;
import com.massivelyflammableapps.shared.dto.offers.OfferDTO;
import com.massivelyflammableapps.shared.resources.STATE;

import org.springframework.beans.factory.annotation.Value;

@Service
public class OffersService {
    @Autowired
    private OffersRepository offersRepository;
    @Autowired
    private OffersByListingRepository offersByListingRepository;
    @Autowired
    private OffersBySellerRepository offersBySellerRepository;
    @Autowired
    private OffersByBuyerRepository offersByBuyerRepository;
    @Autowired
    private OffersBySellerAndBuyerRepository offersBySellerAndBuyerRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${listings.service.queue.name}")
    private String listingsQueueName;

    @CacheEvict(value = "offers_cache", allEntries = true)
    public OfferDTO createOffer(OfferDTO request) {
        Offer newOffer = new Offer(request);

        OfferByListing newOfferByListing = new OfferByListing(request);

        OfferBySeller newOfferBySeller = new OfferBySeller(request);

        OfferByBuyer newOfferByBuyer = new OfferByBuyer(request);

        OfferBySellerAndBuyer newOfferBySellerAndBuyer = new OfferBySellerAndBuyer(request);

        Offer response = offersRepository.save(newOffer);
        offersByListingRepository.save(newOfferByListing);
        offersBySellerRepository.save(newOfferBySeller);
        offersByBuyerRepository.save(newOfferByBuyer);
        offersBySellerAndBuyerRepository.save(newOfferBySellerAndBuyer);

        return response.toDTO();
    }

    @Transactional
    @CacheEvict(value = "offers_cache", allEntries = true)
    public OfferDTO updateOfferStatus(UUID offerId, String status) {
        Offer offer = offersRepository.findById(offerId)
                       .orElseThrow(() -> new RuntimeException("Offer not found"));
        offer.setStatus(status);
        offersRepository.save(offer);

        updateRelatedOfferStatus(offer);

        if ("Accepted".equalsIgnoreCase(status)) {
            sendListingUpdateMessage(offer);
        }
    
        return offer.toDTO();
    }
    
    private void updateRelatedOfferStatus(Offer offer) {
        String status = offer.getStatus();
    
        MapId idForOfferByListing = BasicMapId.id("listingId", offer.getListingId()).with("id", offer.getId());
        Optional<OfferByListing> offerByListings = offersByListingRepository.findById(idForOfferByListing);
        offerByListings.ifPresent(offerByListing -> {
            offerByListing.setStatus(status);
            offersByListingRepository.save(offerByListing);
        });

        MapId idForOfferBySeller = BasicMapId.id("sellerId", offer.getSellerId()).with("id", offer.getId());
        Optional<OfferBySeller> offerBySellers = offersBySellerRepository.findById(idForOfferBySeller);
        offerBySellers.ifPresent(offerBySeller -> {
            offerBySeller.setStatus(status);
            offersBySellerRepository.save(offerBySeller);
        });
    
        MapId idForOfferByBuyer = BasicMapId.id("buyerId", offer.getBuyerId()).with("id", offer.getId());
        Optional<OfferByBuyer> offerByBuyers = offersByBuyerRepository.findById(idForOfferByBuyer);
        offerByBuyers.ifPresent(offerByBuyer -> {
            offerByBuyer.setStatus(status);
            offersByBuyerRepository.save(offerByBuyer);
        });
    
        MapId idForOfferBySellerAndBuyer = BasicMapId.id("sellerId", offer.getSellerId())
                                             .with("buyerId", offer.getBuyerId())
                                             .with("id", offer.getId());
        Optional<OfferBySellerAndBuyer> offerBySellerAndBuyers = offersBySellerAndBuyerRepository.findById(idForOfferBySellerAndBuyer);
        offerBySellerAndBuyers.ifPresent(offerBySellerAndBuyer -> {
            offerBySellerAndBuyer.setStatus(status);
            offersBySellerAndBuyerRepository.save(offerBySellerAndBuyer);
        });
    }

    private void sendListingUpdateMessage(Offer offer) {
        MarkListingDTO markListingDTO = new MarkListingDTO(offer.getListingId(), STATE.SOLD);
        rabbitTemplate.convertAndSend(listingsQueueName, markListingDTO);
    }

    @Cacheable("offers_cache")
    public List<OfferDTO> getAllOffers() {
        var offers = offersRepository.findAll();
        List<OfferDTO> offerDTOs = new ArrayList<>();
        for (Offer offer : offers) {
            offerDTOs.add(offer.toDTO());
        }
        return offerDTOs;
    }

    @Cacheable("offers_cache")
    public List<OfferDTO> getOfferByListing(UUID listingId) {
        var offers = offersByListingRepository.findByListingId(listingId);
        List<OfferDTO> offerDTOs = new ArrayList<>();
        for (OfferByListing offer : offers) {
            offerDTOs.add(offer.toDTO());
        }
        return offerDTOs;
    }

    @Cacheable("offers_cache")
    public List<OfferDTO> getOfferBySeller(UUID sellerId) {
        var offers =  offersBySellerRepository.findBySellerId(sellerId);
        List<OfferDTO> offerDTOs = new ArrayList<>();
        for (OfferBySeller offer : offers) {
            offerDTOs.add(offer.toDTO());
        }
        return offerDTOs;
    }

    @Cacheable("offers_cache")
    public List<OfferDTO> getOfferByBuyer(UUID buyerId) {
        var offers =  offersByBuyerRepository.findByBuyerId(buyerId);
        List<OfferDTO> offerDTOs = new ArrayList<>();
        for (OfferByBuyer offer : offers) {
            offerDTOs.add(offer.toDTO());
        }
        return offerDTOs;
    }

    @Cacheable("offers_cache")
    public List<OfferDTO> getOfferBySellerAndBuyer(UUID sellerId, UUID buyerId) {
        var offers =  offersBySellerAndBuyerRepository.findBySellerIdAndBuyerId(sellerId, buyerId);
        List<OfferDTO> offerDTOs = new ArrayList<>();
        for (OfferBySellerAndBuyer offer : offers) {
            offerDTOs.add(offer.toDTO());
        }
        return offerDTOs;
    }
}
