package com.massivelyflammableapps.listings.service;

import java.util.List;
import java.util.UUID;

import com.massivelyflammableapps.listings.dto.CreateListingDTO;
import com.massivelyflammableapps.listings.dto.GetListingsByGameByProductDTO;
import com.massivelyflammableapps.listings.dto.GetListingsByUserByGameDTO;
import com.massivelyflammableapps.listings.dto.ListingUpdateDTO;
import com.massivelyflammableapps.listings.exceptions.UnauthorizedException;
import com.massivelyflammableapps.listings.model.ListingByGameByProduct;
import com.massivelyflammableapps.listings.model.ListingByUserByGame;
import com.massivelyflammableapps.listings.repository.ListingsByGameByProductRepository;
import com.massivelyflammableapps.listings.repository.ListingsByUserByGameRepository;
import com.massivelyflammableapps.listings.resources.STATE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ListingsService {
    @Autowired
    private ListingsByGameByProductRepository listingsByGameByProductRepository;
    @Autowired
    private ListingsByUserByGameRepository listingsByUserByGameRepository;

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

    public List<ListingByUserByGame> getAllListingsByUserByGame(GetListingsByUserByGameDTO request) {
        List<ListingByUserByGame> listingsByUserByGame = listingsByUserByGameRepository.findByUserIdAndGameIdAndBuying(
                request.getUserId(), request.getGameId(), request.isBuying());
        for(int i = 0; i < listingsByUserByGame.size(); i++) {
            if( listingsByUserByGame.get(i).getState() != STATE.ACTIVE) {
                listingsByUserByGame.remove(i);
                i--;
            }
        }
        return listingsByUserByGame;
    }

    public List<ListingByUserByGame> getAllMyListingsByGame(GetListingsByUserByGameDTO request) {

        // TODO Decode token from the cache
        UUID userId = UUID.fromString("TOKEN DECODE PLS");

        List<ListingByUserByGame> listingsByUserByGame = listingsByUserByGameRepository.findByUserIdAndGameIdAndBuying(
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

        ListingByUserByGame newListingByUserByGame = new ListingByUserByGame(
                userId,
                request.getGameId(),
                request.isBuying(),
                newListingByGameByProduct.getListingId(),
                newListingByGameByProduct.getProductName(),
                newListingByGameByProduct.getProductIcon(),
                newListingByGameByProduct.getQuantity(),
                newListingByGameByProduct.getDesiredOffer());
        listingsByUserByGameRepository.save(newListingByUserByGame);
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
        ListingByUserByGame listingByUserByGame = listingsByUserByGameRepository.findByUserIdAndGameIdAndBuyingAndTimestampAndListingId(
                listingByGameByProduct.getUserId(), listingByGameByProduct.getProductId(), listingByGameByProduct.isBuying(),
                listingByGameByProduct.getTimestamp(), listingByGameByProduct.getListingId());
        listingByUserByGame.setState(request.getState());
        listingsByUserByGameRepository.save(listingByUserByGame);
        return listingsByGameByProductRepository.save(listingByGameByProduct);
    }
}
