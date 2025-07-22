package model;

import java.time.LocalDateTime;

public class Payment {
    private int itemId;
    private int bidId;
    private double amount;
    private String status;
    private LocalDateTime paidAt;

    public Payment(int itemId, int bidId, double amount, String status, LocalDateTime paidAt) {
        this.itemId = itemId;
        this.bidId = bidId;
        this.amount = amount;
        this.status = status;
        this.paidAt = paidAt;
    }

    public int getItemId() {
        return itemId;
    }

    public int getBidId() {
        return bidId;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }
}
