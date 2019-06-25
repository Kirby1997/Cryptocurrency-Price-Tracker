package com.example.jacob.cryptowatcher;


import java.io.Serializable;

/**
 * Created by Jacob on 23/03/2018.
 */
// A coin object
public class Coin implements Serializable {
    private String name;
    private String symbol;
    private String logoURL;
    private float priceUSD;

    Coin(String name, String symbol, String logoURL, float priceUSD) {
        this.name = name;
        this.symbol = symbol;
        this.logoURL = logoURL;
        this.priceUSD = priceUSD;

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

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public float getPriceUSD() {
        return priceUSD;
    }

    public void setPriceUSD(float priceUSD) {
        this.priceUSD = priceUSD;
    }


}
