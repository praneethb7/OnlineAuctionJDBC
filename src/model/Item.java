package model;

public class Item {
    private int itemId;
    private String itemName;
    private String description;
    private double startPrice;
    private double reservePrice;
    private int sellerId;

    public Item(int itemId,String itemName, String description, double startPrice, double reservePrice, int sellerId) {
        this.itemId = itemId;
        this.description = description;
        this.itemName = itemName;
        this.reservePrice = reservePrice;
        this.sellerId = sellerId;
        this.startPrice = startPrice;
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
}
