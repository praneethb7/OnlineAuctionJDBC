package model;

import java.time.LocalDateTime;

public class Item {
    private int itemId;
    private String itemName;
    private String description;
    private double startPrice;
    private double reservePrice;
    private int sellerId;
    private LocalDateTime auctionStartTime;
    private LocalDateTime auctionEndTime;

    public Item(int itemId, String itemName, String description, double startPrice, double reservePrice,
                LocalDateTime auctionStartTime, LocalDateTime auctionEndTime, int sellerId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.startPrice = startPrice;
        this.reservePrice = reservePrice;
        this.auctionStartTime = auctionStartTime;
        this.auctionEndTime = auctionEndTime;
        this.sellerId = sellerId;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public double getReservePrice() {
        return reservePrice;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public void setReservePrice(double reservePrice) {
        this.reservePrice = reservePrice;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public LocalDateTime getAuctionStartTime() {
        return auctionStartTime;
    }

    public void setAuctionStartTime(LocalDateTime auctionStartTime) {
        this.auctionStartTime = auctionStartTime;
    }

    public LocalDateTime getAuctionEndTime() {
        return auctionEndTime;
    }

    public void setAuctionEndTime(LocalDateTime auctionEndTime) {
        this.auctionEndTime = auctionEndTime;
    }
}
