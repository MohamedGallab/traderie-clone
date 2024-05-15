package com.massivelyflammableapps.offers.service;

import java.util.ArrayList;
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

    @Cacheable("${service.cache.name}")
    public List<OfferDTO> getAllOffers() {
        var offers = offersRepository.findAll();
        List<OfferDTO> offerDTOs = new ArrayList<>();
        for (Offer offer : offers) {
            offerDTOs.add(offer.toDTO());
        }
        return offerDTOs;
    }

    @Cacheable("${service.cache.name}")
    public List<OfferDTO> getOfferByListing(UUID listingId) {
        var offers = offersByListingRepository.findByListingId(listingId);
        List<OfferDTO> offerDTOs = new ArrayList<>();
        for (OfferByListing offer : offers) {
            offerDTOs.add(offer.toDTO());
        }
        return offerDTOs;
    }

    @Cacheable("${service.cache.name}")
    public List<OfferDTO> getOfferBySeller(UUID sellerId) {
        var offers =  offersBySellerRepository.findBySellerId(sellerId);
        List<OfferDTO> offerDTOs = new ArrayList<>();
        for (OfferBySeller offer : offers) {
            offerDTOs.add(offer.toDTO());
        }
        return offerDTOs;
    }

    @Cacheable("${service.cache.name}")
    public List<OfferDTO> getOfferByBuyer(UUID buyerId) {
        var offers =  offersByBuyerRepository.findByBuyerId(buyerId);
        List<OfferDTO> offerDTOs = new ArrayList<>();
        for (OfferByBuyer offer : offers) {
            offerDTOs.add(offer.toDTO());
        }
        return offerDTOs;
    }

    @Cacheable("${service.cache.name}")
    public List<OfferDTO> getOfferBySellerAndBuyer(UUID sellerId, UUID buyerId) {
        var offers =  offersBySellerAndBuyerRepository.findBySellerIdAndBuyerId(sellerId, buyerId);
        List<OfferDTO> offerDTOs = new ArrayList<>();
        for (OfferBySellerAndBuyer offer : offers) {
            offerDTOs.add(offer.toDTO());
        }
        return offerDTOs;
    }
}
