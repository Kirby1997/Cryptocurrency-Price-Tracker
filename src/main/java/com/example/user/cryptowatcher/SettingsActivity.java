package com.example.jacob.cryptowatcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class SettingsActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        //banner ad
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("4F1F603A9B267B0FBC1FB7C8FE51361D")
                .build();
        mAdView.loadAd(adRequest);

        //interstitial ad
        mInterstitialAd = new InterstitialAd(this);
        //setting to the test ads
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder()
                //ensure my phone is set to a test device
                .addTestDevice("4F1F603A9B267B0FBC1FB7C8FE51361D")
                .build());
        mInterstitialAd.setAdListener(new AdListener(){

        @Override
         public void onAdClosed() {
            super.onAdClosed();
            finish();
            }
        });
    }


    public void showInterstitial(){

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            finish();
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }

    }



    @Override
    public void onBackPressed() {
        //show ad of app exit
        showInterstitial();

    }
}
