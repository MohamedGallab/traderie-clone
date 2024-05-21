package com.massivelyflammableapps.listings.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.massivelyflammableapps.shared.dto.listings.*;
import com.massivelyflammableapps.shared.resources.STATE;
import com.massivelyflammableapps.listings.model.Listing;
import com.massivelyflammableapps.listings.model.ListingByGameByProduct;
import com.massivelyflammableapps.listings.model.ListingByGameByUser;
import com.massivelyflammableapps.listings.repository.ListingsByGameByProductRepository;
import com.massivelyflammableapps.listings.repository.ListingsByGameByUserRepository;
import com.massivelyflammableapps.listings.repository.ListingsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ListingsService {
    @Autowired
    private ListingsByGameByProductRepository listingsByGameByProductRepository;
    @Autowired
    private ListingsByGameByUserRepository listingsByGameByUserRepository;
    @Autowired
    private ListingsRepository listingsRepository;

    @Cacheable("listingsCache")
    public List<ListingDTO> getAllListingsByGameByProduct(GetListingsByGameByProductDTO request) {
        List<ListingByGameByProduct> listingsByGameByProduct = listingsByGameByProductRepository
                .findByGameIdAndProductIdAndBuying(
                        request.getGameId(), request.getProductId(), request.isBuying());
        List<ListingDTO> listingsByGameByProductDTO = new ArrayList<ListingDTO>();
        for (int i = 0; i < listingsByGameByProduct.size(); i++) {
            if (listingsByGameByProduct.get(i).getState() == STATE.ACTIVE) {
                listingsByGameByProductDTO.add(listingsByGameByProduct.get(i).toDTO());
            }
        }
        return listingsByGameByProductDTO;
    }

    @Cacheable("listingsCache")
    public List<ListingDTO> getAllListingsByGameByUser(GetListingsByGameByUserDTO request) {
        List<ListingByGameByUser> listingsByUserByGame = listingsByGameByUserRepository.findByUserIdAndGameIdAndBuying(
                request.getUserId(), request.getGameId(), request.isBuying());
        List<ListingDTO> listingsByGameByProductDTO = new ArrayList<ListingDTO>();
        for (int i = 0; i < listingsByUserByGame.size(); i++) {
            if (listingsByUserByGame.get(i).getState() == STATE.ACTIVE) {
                listingsByGameByProductDTO.add(listingsByUserByGame.get(i).toDTO());
            }
        }
        return listingsByGameByProductDTO;
    }

    @Cacheable("listingsCache")
    public List<ListingDTO> getAllMyListingsByGame(GetMyListingsByGameDTO request) {

        // TODO Decode token from the cache(USER SERVICE)
        // UUID userId = UUID.fromString("TOKEN DECODE PLS");
        // this is hard coded for testing !!!!!!!!!!!!!!!!
        UUID userId = UUID.fromString("c2bc283c-9684-4974-99b8-1ef072ebb0ec");

        List<ListingByGameByUser> listingsByUserByGame = listingsByGameByUserRepository.findByUserIdAndGameIdAndBuying(
                userId, request.getGameId(), request.isBuying());
        List<ListingDTO> listingsByGameByProductDTO = new ArrayList<ListingDTO>();
        for (int i = 0; i < listingsByUserByGame.size(); i++) {
            if (request.isHistory() && listingsByUserByGame.get(i).getState() == STATE.ACTIVE) {
                continue;
            }
            if (!request.isHistory() && listingsByUserByGame.get(i).getState() != STATE.ACTIVE) {
                continue;
            }
            listingsByGameByProductDTO.add(listingsByUserByGame.get(i).toDTO());
        }
        return listingsByGameByProductDTO;
    }

    @CacheEvict(value = "listingsCache", allEntries = true)
    public ListingDTO createListing(ListingDTO request) {
        // TODO Decode token from the cache(USER SERVICE)
        // request.setUserId(UUID.fromString("TOKEN DECODE PLS"));
        Listing newListing = new Listing(request);

        request.setListingId(newListing.getListingId());
        System.out.println("Fukcer!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        System.out.println("newListing: " + newListing);

        listingsRepository.save(newListing);
        System.out.println("NoError!");

        ListingByGameByProduct newListingByGameByProduct = new ListingByGameByProduct(request);
        listingsByGameByProductRepository.save(newListingByGameByProduct);

        System.out.println("newListingByGameByProduct: " + newListingByGameByProduct);

        ListingByGameByUser newListingByGameByUser = new ListingByGameByUser(
                request.getUserId(),
                request.getGameId(),
                request.isBuying(),
                newListingByGameByProduct.getListingId(),
                newListingByGameByProduct.getProductName(),
                newListingByGameByProduct.getProductIcon(),
                newListingByGameByProduct.getQuantity(),
                newListingByGameByProduct.getDesiredOffer(),
                newListingByGameByProduct.getProductId());
        listingsByGameByUserRepository.save(newListingByGameByUser);

        System.out.println("newListingByGameByUser: " + newListingByGameByUser);

        System.out.println("newListingByGameByProductDTO: " + newListingByGameByProduct.toDTO());
        return newListingByGameByProduct.toDTO();
    }

    @CacheEvict(value = "listingsCache", allEntries = true)
    public ListingDTO updateListingState(ListingUpdateDTO request) {

        ListingByGameByProduct listingByGameByProduct = listingsByGameByProductRepository
                .findByGameIdAndProductIdAndBuyingAndListingId(
                        request.getGameId(), request.getProductId(), request.isBuying(), request.getListingId());
        // TODO Decode token from the cache(USER SERVICE)
        // UUID userId = UUID.fromString("TOKEN DECODE PLS");
        // // TODO I dont know what deez is
        // if (userId != request.getUserId()) {
        // throw new UnauthorizedException();
        // }

        listingByGameByProduct.setState(request.getState());
        ListingByGameByUser listingByGameByUser = listingsByGameByUserRepository
                .findByUserIdAndGameIdAndBuyingAndListingId(
                        listingByGameByProduct.getUserId(), listingByGameByProduct.getGameId(),
                        listingByGameByProduct.getBuying(),
                        listingByGameByProduct.getListingId());
        listingByGameByUser.setState(request.getState());
        listingsByGameByUserRepository.save(listingByGameByUser);
        return listingsByGameByProductRepository.save(listingByGameByProduct).toDTO();
    }

    @CacheEvict(value = "listingsCache", allEntries = true)
    public boolean markListing(MarkListingDTO request) {
        try {
            Listing listing = listingsRepository.findByListingId(request.getListingId());
            ListingByGameByProduct listingByGameByProduct = listingsByGameByProductRepository
                    .findByGameIdAndProductIdAndBuyingAndListingId(
                            listing.getGameId(), listing.getProductId(), listing.getBuying(), listing.getListingId());

            listingByGameByProduct.setState(request.getState());
            ListingByGameByUser listingByGameByUser = listingsByGameByUserRepository
                    .findByUserIdAndGameIdAndBuyingAndListingId(
                            listingByGameByProduct.getUserId(), listingByGameByProduct.getGameId(),
                            listingByGameByProduct.getBuying(),
                            listingByGameByProduct.getListingId());
            listingByGameByUser.setState(request.getState());
            listingsByGameByUserRepository.save(listingByGameByUser);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
