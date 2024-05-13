package com.massivelyflammableapps.listings.service;

import java.util.List;
import java.util.UUID;
import com.massivelyflammableapps.listings.dto.CreateListingDTO;
import com.massivelyflammableapps.listings.dto.GetListingsByGameByProductDTO;
import com.massivelyflammableapps.listings.dto.GetListingsByGameByUserDTO;
import com.massivelyflammableapps.listings.dto.ListingUpdateDTO;
import com.massivelyflammableapps.listings.exceptions.UnauthorizedException;
import com.massivelyflammableapps.listings.model.ListingByGameByProduct;
import com.massivelyflammableapps.listings.model.ListingByGameByUser;
import com.massivelyflammableapps.listings.repository.ListingsByGameByProductRepository;
import com.massivelyflammableapps.listings.repository.ListingsByGameByUserRepository;
import com.massivelyflammableapps.listings.resources.STATE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ListingsService {
    @Autowired
    private ListingsByGameByProductRepository listingsByGameByProductRepository;
    @Autowired
    private ListingsByGameByUserRepository listingsByGameByUserRepository;

    @Cacheable("listingsCache")
    public List<ListingByGameByProduct> getAllListingsByGameByProduct(GetListingsByGameByProductDTO request) {
        List<ListingByGameByProduct> listingsByGameByProduct = listingsByGameByProductRepository.findByGameIdAndProductIdAndBuying(
                request.getGameId(), request.getProductId(), request.isBuying());
        for(int i = 0; i < listingsByGameByProduct.size(); i++) {
            if(listingsByGameByProduct.get(i).getState() != STATE.ACTIVE) {
                listingsByGameByProduct.remove(i);
                i--;
            }
        }
        return listingsByGameByProduct;
    }
    @Cacheable("listingsCache")
    public List<ListingByGameByUser> getAllListingsByGameByUser(GetListingsByGameByUserDTO request) {
        List<ListingByGameByUser> listingsByUserByGame = listingsByGameByUserRepository.findByUserIdAndGameIdAndBuying(
                request.getUserId(), request.getGameId(), request.isBuying());
        for(int i = 0; i < listingsByUserByGame.size(); i++) {
            if( listingsByUserByGame.get(i).getState() != STATE.ACTIVE) {
                listingsByUserByGame.remove(i);
                i--;
            }
        }
        return listingsByUserByGame;
    }
    @Cacheable("listingsCache")
    public List<ListingByGameByUser> getAllMyListingsByGame(GetListingsByGameByUserDTO request) {

        // TODO Decode token from the cache
        UUID userId = UUID.fromString("TOKEN DECODE PLS");

        List<ListingByGameByUser> listingsByUserByGame = listingsByGameByUserRepository.findByUserIdAndGameIdAndBuying(
                userId, request.getGameId(), request.isBuying());
        for(int i = 0; i < listingsByUserByGame.size(); i++) {
            if(request.isHistory() && listingsByUserByGame.get(i).getState() == STATE.ACTIVE) {
                listingsByUserByGame.remove(i);
                i--;
            }
            if(!request.isHistory() && listingsByUserByGame.get(i).getState() != STATE.ACTIVE) {
                listingsByUserByGame.remove(i);
                i--;
            }
        }
        return listingsByUserByGame;
    }

    public ListingByGameByProduct createListing(CreateListingDTO request) {
        // TODO Decode token from the cache
        UUID userId = UUID.fromString("TOKEN DECODE PLS");

        ListingByGameByProduct newListingByGameByProduct = new ListingByGameByProduct(
                request.getProductId(),
                request.isBuying(),
                request.getGameId(),
                request.getProductName(),
                request.getProductIcon(),
                request.getQuantity(),
                userId,
                request.getDesiredOffer());
        listingsByGameByProductRepository.save(newListingByGameByProduct);

        ListingByGameByUser newListingByGameByUser = new ListingByGameByUser(
                userId,
                request.getGameId(),
                request.isBuying(),
                newListingByGameByProduct.getListingId(),
                newListingByGameByProduct.getProductName(),
                newListingByGameByProduct.getProductIcon(),
                newListingByGameByProduct.getQuantity(),
                newListingByGameByProduct.getDesiredOffer());
        listingsByGameByUserRepository.save(newListingByGameByUser);
        return newListingByGameByProduct;
    }

    public ListingByGameByProduct updateListingState(ListingUpdateDTO request) throws UnauthorizedException {
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
                listingByGameByProduct.getUserId(), listingByGameByProduct.getProductId(), listingByGameByProduct.isBuying(),
                listingByGameByProduct.getTimestamp(), listingByGameByProduct.getListingId());
        listingByGameByUser.setState(request.getState());
        listingsByGameByUserRepository.save(listingByGameByUser);
        return listingsByGameByProductRepository.save(listingByGameByProduct);
    }
}
