package com.massivelyflammableapps.listings.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.massivelyflammableapps.resources.STATE;
import com.massivelyflammableapps.shared.dto.listings.*;
import com.massivelyflammableapps.listings.exceptions.UnauthorizedException;
import com.massivelyflammableapps.listings.model.ListingByGameByProduct;
import com.massivelyflammableapps.listings.model.ListingByGameByUser;
import com.massivelyflammableapps.listings.repository.ListingsByGameByProductRepository;
import com.massivelyflammableapps.listings.repository.ListingsByGameByUserRepository;
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

    @Cacheable("listingsCache")
    public List<ListingDTO> getAllListingsByGameByProduct(GetListingsByGameByProductDTO request) {
        List<ListingByGameByProduct> listingsByGameByProduct = listingsByGameByProductRepository.findByGameIdAndProductIdAndBuying(
                request.getGameId(), request.getProductId(), request.isBuying());
        List<ListingDTO> listingsByGameByProductDTO = new ArrayList<ListingDTO>();
        for(int i = 0; i < listingsByGameByProduct.size(); i++) {
            if(listingsByGameByProduct.get(i).getState() == STATE.ACTIVE) {
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
        for(int i = 0; i < listingsByUserByGame.size(); i++) {
            if(listingsByUserByGame.get(i).getState() == STATE.ACTIVE) {
                listingsByGameByProductDTO.add(listingsByUserByGame.get(i).toDTO());
            }
        }
        return listingsByGameByProductDTO;
    }
    @Cacheable("listingsCache")
    public List<ListingDTO> getAllMyListingsByGame(GetMyListingsByGameDTO request) {

        // TODO Decode token from the cache
        UUID userId = UUID.fromString("TOKEN DECODE PLS");

        List<ListingByGameByUser> listingsByUserByGame = listingsByGameByUserRepository.findByUserIdAndGameIdAndBuying(
                userId, request.getGameId(), request.isBuying());
        List<ListingDTO> listingsByGameByProductDTO = new ArrayList<ListingDTO>();
        for(int i = 0; i < listingsByUserByGame.size(); i++) {
            if(request.isHistory() && listingsByUserByGame.get(i).getState() == STATE.ACTIVE) {
              continue;
            }
            if(!request.isHistory() && listingsByUserByGame.get(i).getState() != STATE.ACTIVE) {
                continue;
            }
            listingsByGameByProductDTO.add(listingsByUserByGame.get(i).toDTO());
        }
        return listingsByGameByProductDTO;
    }
    @CacheEvict(value = "listingsCache", allEntries = true)
    public ListingDTO createListing(ListingDTO request) {
        // TODO Decode token from the cache
        request.setUserId(UUID.fromString("TOKEN DECODE PLS"));

        ListingByGameByProduct newListingByGameByProduct = new ListingByGameByProduct(request);
        listingsByGameByProductRepository.save(newListingByGameByProduct);

        ListingByGameByUser newListingByGameByUser = new ListingByGameByUser(
                request.getUserId(),
                request.getGameId(),
                request.isBuying(),
                newListingByGameByProduct.getListingId(),
                newListingByGameByProduct.getProductName(),
                newListingByGameByProduct.getProductIcon(),
                newListingByGameByProduct.getQuantity(),
                newListingByGameByProduct.getDesiredOffer());
        listingsByGameByUserRepository.save(newListingByGameByUser);
        return newListingByGameByProduct.toDTO();
    }
    @CacheEvict(value = "listingsCache", allEntries = true)
    public ListingDTO updateListingState(ListingUpdateDTO request) throws UnauthorizedException {
        // TODO Decode token from the cache
        UUID userId = UUID.fromString("TOKEN DECODE PLS");
        // TODO I dont know what deez is
        if(userId != request.getUserId()) {
            throw new UnauthorizedException();
        }
        ListingByGameByProduct listingByGameByProduct = listingsByGameByProductRepository.findByListingIdAndTimestampAndProductIdAndBuying(
                request.getListingId(), request.getTimestamp(), request.getProductId(), request.isBuying());
        listingByGameByProduct.setState(request.getState());
        ListingByGameByUser listingByGameByUser = listingsByGameByUserRepository.findByUserIdAndGameIdAndBuyingAndTimestampAndListingId(
                listingByGameByProduct.getUserId(), listingByGameByProduct.getProductId(), listingByGameByProduct.getBuying(),
                listingByGameByProduct.getTimestamp(), listingByGameByProduct.getListingId());
        listingByGameByUser.setState(request.getState());
        listingsByGameByUserRepository.save(listingByGameByUser);
        return listingsByGameByProductRepository.save(listingByGameByProduct).toDTO();
    }
}
