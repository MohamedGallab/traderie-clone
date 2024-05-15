package com.massivelyflammableapps.offers.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.massivelyflammableapps.offers.model.Offer;
import com.massivelyflammableapps.offers.model.OfferByBuyer;
import com.massivelyflammableapps.offers.model.OfferByListing;
import com.massivelyflammableapps.offers.model.OfferBySeller;
import com.massivelyflammableapps.offers.model.OfferBySellerAndBuyer;
import com.massivelyflammableapps.offers.model.OfferedProduct;
import com.massivelyflammableapps.offers.repository.OffersByBuyerRepository;
import com.massivelyflammableapps.offers.repository.OffersByListingRepository;
import com.massivelyflammableapps.offers.repository.OffersBySellerAndBuyerRepository;
import com.massivelyflammableapps.offers.repository.OffersBySellerRepository;
import com.massivelyflammableapps.offers.repository.OffersRepository;
import com.massivelyflammableapps.shared.dto.offers.OfferDTO;
import com.massivelyflammableapps.shared.dto.offers.OfferedProductDTO;

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
        List<List<OfferedProduct>> offeredProducts = new ArrayList<>();

        for (List<OfferedProductDTO> offeredProductList : request.getOfferedProducts()) {
            var tempOfferedProduct = new ArrayList<OfferedProduct>();
            for (OfferedProductDTO item : offeredProductList) {
                tempOfferedProduct.add(new OfferedProduct(
                        item.getId(),
                        item.getGameId(),
                        item.getProductId(),
                        item.getQuantity(),
                        item.getProductName(),
                        item.getProductIcon()
                        ));
            }
            offeredProducts.add(tempOfferedProduct);
        }

        Offer newOffer = new Offer(
                request.getListingId(),
                request.getBuyerId(),
                request.getSellerId(),
                request.getStatus(),
                offeredProducts);

        OfferByListing newOfferByListing = new OfferByListing(
                newOffer.getId(),
                request.getListingId(),
                request.getBuyerId(),
                request.getSellerId(),
                newOffer.getTimestamp(),
                request.getStatus(),
                offeredProducts);

        OfferBySeller newOfferBySeller = new OfferBySeller(
                newOffer.getId(),
                request.getListingId(),
                request.getBuyerId(),
                request.getSellerId(),
                newOffer.getTimestamp(),
                request.getStatus(),
                offeredProducts);

        OfferByBuyer newOfferByBuyer = new OfferByBuyer(
                newOffer.getId(),
                request.getListingId(),
                request.getBuyerId(),
                request.getSellerId(),
                newOffer.getTimestamp(),
                request.getStatus(),
                offeredProducts);

        OfferBySellerAndBuyer newOfferBySellerAndBuyer = new OfferBySellerAndBuyer(
                newOffer.getId(),
                request.getListingId(),
                request.getBuyerId(),
                request.getSellerId(),
                newOffer.getTimestamp(),
                request.getStatus(),
                offeredProducts);

        Offer response = offersRepository.save(newOffer);
        offersByListingRepository.save(newOfferByListing);
        offersBySellerRepository.save(newOfferBySeller);
        offersByBuyerRepository.save(newOfferByBuyer);
        offersBySellerAndBuyerRepository.save(newOfferBySellerAndBuyer);

        return response.toDTO();
    }

    @Cacheable(value = "offers_cache")
    public List<OfferDTO> getAllOffers() {
        var offers = offersRepository.findAll();
        List<OfferDTO> offerDTOs = new ArrayList<>();
        for (Offer offer : offers) {
            offerDTOs.add(offer.toDTO());
        }
        return offerDTOs;
    }

    @Cacheable(value = "offers_cache")
    public List<OfferByListing> getOfferByListing(UUID listingId) {
        return offersByListingRepository.findByListingId(listingId);
    }

    @Cacheable(value = "offers_cache")
    public List<OfferBySeller> getOfferBySeller(UUID sellerId) {
        return offersBySellerRepository.findBySellerId(sellerId);
    }

    @Cacheable(value = "offers_cache")
    public List<OfferByBuyer> getOfferByBuyer(UUID buyerId) {
        return offersByBuyerRepository.findByBuyerId(buyerId);
    }

    @Cacheable(value = "offers_cache")
    public List<OfferBySellerAndBuyer> getOfferBySellerAndBuyer(UUID sellerId, UUID buyerId) {
        return offersBySellerAndBuyerRepository.findBySellerIdAndBuyerId(sellerId, buyerId);
    }
}
