package model;

import java.time.LocalDateTime;

public class Bid {
    private int bidId;
    private int itemId;
    private int bidderId;
    private double amount;
    private LocalDateTime bidTime;

    public Bid(int bidId, int itemId, int bidderId, double amount, LocalDateTime bidTime) {
        this.bidId = bidId;
        this.itemId = itemId;
        this.bidderId = bidderId;
        this.amount = amount;
        this.bidTime = bidTime;
    }

    public int getBidId() {
        return bidId;
    }

    public int getItemId() {
        return itemId;
    }

    public int getBidderId() {
        return bidderId;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getBidTime() {
        return bidTime;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setBidTime(LocalDateTime bidTime) {
        this.bidTime = bidTime;
    }
}
