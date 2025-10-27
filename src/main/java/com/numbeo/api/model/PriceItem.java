package com.numbeo.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a single price item from the Numbeo API
 */
public class PriceItem {

    @SerializedName("item_id")
    private Integer itemId;

    @SerializedName("item_name")
    private String itemName;

    @SerializedName("category")
    private String category;

    @SerializedName("data_points")
    private Integer dataPoints;

    @SerializedName("lowest_price")
    private Double lowestPrice;

    @SerializedName("average_price")
    private Double averagePrice;

    @SerializedName("highest_price")
    private Double highestPrice;

    // Constructors
    public PriceItem() {
    }

    // Getters and Setters
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(Integer dataPoints) {
        this.dataPoints = dataPoints;
    }

    public Double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(Double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Double getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(Double highestPrice) {
        this.highestPrice = highestPrice;
    }

    @Override
    public String toString() {
        return String.format("%-50s | Avg: $%-8.2f | Range: $%.2f - $%.2f | Data Points: %d",
                itemName != null ? itemName : "N/A",
                averagePrice != null ? averagePrice : 0.0,
                lowestPrice != null ? lowestPrice : 0.0,
                highestPrice != null ? highestPrice : 0.0,
                dataPoints != null ? dataPoints : 0);
    }
}
