package com.massivelyflammableapps.offers.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import com.massivelyflammableapps.shared.dto.offers.OfferDTO;

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
            //sendListingUpdateMessage(offer);
        }
    
        return offer.toDTO();
    }
    
    private void updateRelatedOfferStatus(Offer offer) {
        UUID listingId = offer.getListingId();
        UUID sellerId = offer.getSellerId();
        UUID buyerId = offer.getBuyerId();
        String status = offer.getStatus();
    
        List<OfferByListing> offerByListings = offersByListingRepository.findByListingId(listingId);
        offerByListings.forEach(offerByListing -> {
            offerByListing.setStatus(status);
            offersByListingRepository.save(offerByListing);
        });
    
        List<OfferBySeller> offerBySellers = offersBySellerRepository.findBySellerId(sellerId);
        offerBySellers.forEach(offerBySeller -> {
            offerBySeller.setStatus(status);
            offersBySellerRepository.save(offerBySeller);
        });
    
        List<OfferByBuyer> offerByBuyers = offersByBuyerRepository.findByBuyerId(buyerId);
        offerByBuyers.forEach(offerByBuyer -> {
            offerByBuyer.setStatus(status);
            offersByBuyerRepository.save(offerByBuyer);
        });
    
        List<OfferBySellerAndBuyer> offerBySellerAndBuyers = offersBySellerAndBuyerRepository.findBySellerIdAndBuyerId(sellerId, buyerId);
        offerBySellerAndBuyers.forEach(offerBySellerAndBuyer -> {
            offerBySellerAndBuyer.setStatus(status);
            offersBySellerAndBuyerRepository.save(offerBySellerAndBuyer);
        });
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
