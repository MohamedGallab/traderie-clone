package com.massivelyflammableapps.offers.controller;

import org.springframework.web.bind.annotation.RestController;

import com.massivelyflammableapps.offers.model.Offer;
import com.massivelyflammableapps.offers.model.OfferByListing;
import com.massivelyflammableapps.offers.model.OfferBySeller;
import com.massivelyflammableapps.offers.model.OfferByBuyer;
import com.massivelyflammableapps.offers.model.OfferBySellerAndBuyer;
import com.massivelyflammableapps.offers.repository.OffersByBuyerRepository;
import com.massivelyflammableapps.offers.repository.OffersByListingRepository;
import com.massivelyflammableapps.offers.repository.OffersBySellerAndBuyerRepository;
import com.massivelyflammableapps.offers.repository.OffersBySellerRepository;
import com.massivelyflammableapps.offers.repository.OffersRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/offers")
public class OffersController {

    @Autowired
    OffersRepository offersRepository;
    @Autowired
    OffersByListingRepository offersByListingRepository;
    @Autowired
    OffersBySellerRepository offersBySellerRepository;
    @Autowired
    OffersByBuyerRepository offersByBuyerRepository;
    @Autowired
    OffersBySellerAndBuyerRepository offersBySellerAndBuyerRepository;

    @GetMapping
    public ResponseEntity<List<Offer>> getMethodName() {
        return offersRepository.findAll().isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(offersRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Offer> postMethodName(@RequestBody Offer request) {
        try {
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

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/getByListing")
    public List<OfferByListing> getOfferByListing(@RequestParam UUID listingId)
    {
        return offersByListingRepository.findByListingId(listingId);
    }

    @GetMapping("/getBySeller")
    public List<OfferBySeller> getOfferBySeller(@RequestParam UUID sellerId)
    {
        return offersBySellerRepository.findBySellerId(sellerId);
    }

    @GetMapping("/getByBuyer")
    public List<OfferByBuyer> getOfferByBuyer(@RequestParam UUID buyerId)
    {
        return offersByBuyerRepository.findByBuyerId(buyerId);
    }

    @GetMapping("/getBySellerAndBuyer")
    public List<OfferBySellerAndBuyer> getOfferBySellerAndBuyer(@RequestParam UUID sellerId,@RequestParam UUID buyerId)
    {
        return offersBySellerAndBuyerRepository.findBySellerIdAndBuyerId(sellerId,buyerId);
    }
}