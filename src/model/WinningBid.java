package model;

import java.time.LocalDateTime;

public class WinningBid {
    private int itemId;
    private int bidId;
    private double conversionFee;
    private double transactionFee;
    private String paymentStatus;
    private LocalDateTime createdAt;

    public WinningBid(int itemId, int bidId, double conversionFee, double transactionFee, String paymentStatus, LocalDateTime createdAt) {
        this.itemId = itemId;
        this.bidId = bidId;
        this.conversionFee = conversionFee;
        this.transactionFee = transactionFee;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
    }

    public int getItemId() {
        return itemId;
    }

    public int getBidId() {
        return bidId;
    }

    public double getConversionFee() {
        return conversionFee;
    }

    public double getTransactionFee() {
        return transactionFee;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
