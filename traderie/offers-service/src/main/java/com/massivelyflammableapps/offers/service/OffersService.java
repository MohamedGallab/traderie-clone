package com.massivelyflammableapps.offers.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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

    @Cacheable("offersCache")
    public List<Offer> getAllOffers() {
        return offersRepository.findAll();
    }

    public Offer createOffer(Offer request) {
        Offer newOffer = new Offer(
                request.getListingId(),
                request.getBuyerId(),
                request.getSellerId(),
                request.getStatus(),
                request.getOfferedProducts());

        OfferByListing newOfferByListing = new OfferByListing(
                newOffer.getId(),
                request.getListingId(),
                request.getBuyerId(),
                request.getSellerId(),
                newOffer.getTimestamp(),
                request.getStatus(),
                request.getOfferedProducts());

        OfferBySeller newOfferBySeller = new OfferBySeller(
                newOffer.getId(),
                request.getListingId(),
                request.getBuyerId(),
                request.getSellerId(),
                newOffer.getTimestamp(),
                request.getStatus(),
                request.getOfferedProducts());

        OfferByBuyer newOfferByBuyer = new OfferByBuyer(
                newOffer.getId(),
                request.getListingId(),
                request.getBuyerId(),
                request.getSellerId(),
                newOffer.getTimestamp(),
                request.getStatus(),
                request.getOfferedProducts());

        OfferBySellerAndBuyer newOfferBySellerAndBuyer = new OfferBySellerAndBuyer(
                newOffer.getId(),
                request.getListingId(),
                request.getBuyerId(),
                request.getSellerId(),
                newOffer.getTimestamp(),
                request.getStatus(),
                request.getOfferedProducts());

        Offer response = offersRepository.save(newOffer);
        offersByListingRepository.save(newOfferByListing);
        offersBySellerRepository.save(newOfferBySeller);
        offersByBuyerRepository.save(newOfferByBuyer);
        offersBySellerAndBuyerRepository.save(newOfferBySellerAndBuyer);

        return response;
    }

    @Cacheable("offersCache")
    public List<OfferByListing> getOfferByListing(UUID listingId) {
        return offersByListingRepository.findByListingId(listingId);
    }

    @Cacheable("offersCache")
    public List<OfferBySeller> getOfferBySeller(UUID sellerId) {
        return offersBySellerRepository.findBySellerId(sellerId);
    }

    @Cacheable("offersCache")
    public List<OfferByBuyer> getOfferByBuyer(UUID buyerId) {
        return offersByBuyerRepository.findByBuyerId(buyerId);
    }

    @Cacheable("offersCache")
    public List<OfferBySellerAndBuyer> getOfferBySellerAndBuyer(UUID sellerId, UUID buyerId) {
        return offersBySellerAndBuyerRepository.findBySellerIdAndBuyerId(sellerId, buyerId);
    }
}
