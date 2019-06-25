package com.example.jacob.cryptowatcher;



/**
 * Created by Jacob on 23/03/2018.
 */

public class CoinToggle {
    private String name;
    private String logoURL;
    private int enabled;


    CoinToggle(String name, String logoURL, int enabled) {
        this.name = name;
        this.logoURL = logoURL;
        this.enabled = enabled;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }
}
