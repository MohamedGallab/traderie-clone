package com.massivelyflammableapps.offers;

import lombok.*;
import java.util.UUID;
import java.util.Set;
import java.sql.Timestamp;


import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Offer {
    @PrimaryKey
    private UUID id;
    private String listingId;
    private String buyerId;
    private String sellerId;
    private Timestamp timestamp;
    private String status;
    private Set<Set<OfferedProduct>> offeredProducts;
    
    public Offer(UUID offerId, String listingId, String buyerId, String sellerId, Timestamp timestamp, String status, Set<Set<OfferedProduct>> offeredProducts) {
        this.id = offerId;
        this.listingId = listingId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.timestamp = timestamp;
        this.status = status;
        this.offeredProducts = offeredProducts;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Set<OfferedProduct>> getOfferedProducts() {
        return offeredProducts;
    }

    public void setOfferedProducts(Set<Set<OfferedProduct>> offeredProducts) {
        this.offeredProducts = offeredProducts;
    }

}
