package com.numbeo.api.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Represents the response from the Numbeo city prices API
 */
public class CityPricesResponse {

    @SerializedName("name")
    private String cityName;

    @SerializedName("country")
    private String country;

    @SerializedName("currency")
    private String currency;

    @SerializedName("prices")
    private List<PriceItem> prices;

    @SerializedName("contributors_12months")
    private Integer contributors12Months;

    @SerializedName("month_last_update")
    private Integer monthLastUpdate;

    @SerializedName("year_last_update")
    private Integer yearLastUpdate;

    // Constructors
    public CityPricesResponse() {
    }

    // Getters and Setters
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<PriceItem> getPrices() {
        return prices;
    }

    public void setPrices(List<PriceItem> prices) {
        this.prices = prices;
    }

    public Integer getContributors12Months() {
        return contributors12Months;
    }

    public void setContributors12Months(Integer contributors12Months) {
        this.contributors12Months = contributors12Months;
    }

    public Integer getMonthLastUpdate() {
        return monthLastUpdate;
    }

    public void setMonthLastUpdate(Integer monthLastUpdate) {
        this.monthLastUpdate = monthLastUpdate;
    }

    public Integer getYearLastUpdate() {
        return yearLastUpdate;
    }

    public void setYearLastUpdate(Integer yearLastUpdate) {
        this.yearLastUpdate = yearLastUpdate;
    }
}
