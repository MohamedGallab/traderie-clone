package com.massivelyflammableapps.offers;

public class OfferedProduct {
    
    private int quantity;
    private String gameId;
    private String productId;
    private String productName;
    private String productIcon;

    public OfferedProduct(int quantity, String gameId, String productId, String productName, String productIcon) {
        this.quantity = quantity;
        this.gameId = gameId;
        this.productId = productId;
        this.productName = productName;
        this.productIcon = productIcon;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }
}
