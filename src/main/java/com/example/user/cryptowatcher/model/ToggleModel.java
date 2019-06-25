package com.example.user.cryptowatcher.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ToggleModel {


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("symbol")
    @Expose
    private String symbol;

    @SerializedName("rank")
    @Expose
    private int rank;

    @SerializedName("price_usd")
    @Expose
    private float price_usd;

    @SerializedName("price_btc")
    @Expose
    private float price_btc;

    @SerializedName("24h_volume_usd")
    @Expose
    private float volume_usd;

    @SerializedName("market_cap_usd")
    @Expose
    private float market_cap_usd;

    @SerializedName("available_supply")
    @Expose
    private float available_supply;

    @SerializedName("total_supply")
    @Expose
    private float total_supply;

    @SerializedName("percentage_change_1h")
    @Expose
    private float percentage_change_1h;

    @SerializedName("percentage_change_24h")
    @Expose
    private float percentage_change_24h;

    @SerializedName("percentage_change7d")
    @Expose
    private float percentage_change_7d;

    @SerializedName("last_updated")
    @Expose
    private int last_updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public float getPrice_usd() {
        return price_usd;
    }

    public void setPrice_usd(float price_usd) {
        this.price_usd = price_usd;
    }

    public float getPrice_btc() {
        return price_btc;
    }

    public void setPrice_btc(float price_btc) {
        this.price_btc = price_btc;
    }

    public float getVolume_usd() {
        return volume_usd;
    }

    public void setVolume_usd(float volume_usd) {
        this.volume_usd = volume_usd;
    }

    public float getMarket_cap_usd() {
        return market_cap_usd;
    }

    public void setMarket_cap_usd(float market_cap_usd) {
        this.market_cap_usd = market_cap_usd;
    }

    public float getAvailable_supply() {
        return available_supply;
    }

    public void setAvailable_supply(float available_supply) {
        this.available_supply = available_supply;
    }

    public float getTotal_supply() {
        return total_supply;
    }

    public void setTotal_supply(float total_supply) {
        this.total_supply = total_supply;
    }

    public float getPercentage_change_1h() {
        return percentage_change_1h;
    }

    public void setPercentage_change_1h(float percentage_change_1h) {
        this.percentage_change_1h = percentage_change_1h;
    }

    public float getPercentage_change_24h() {
        return percentage_change_24h;
    }

    public void setPercentage_change_24h(float percentage_change_24h) {
        this.percentage_change_24h = percentage_change_24h;
    }

    public float getPercentage_change_7d() {
        return percentage_change_7d;
    }

    public void setPercentage_change_7d(float percentage_change_7d) {
        this.percentage_change_7d = percentage_change_7d;
    }

    public int getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(int last_updated) {
        this.last_updated = last_updated;
    }



}
