package com.example.user.cryptowatcher;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.user.cryptowatcher.database.DbHelper;

public class CoinInformationActivity extends AppCompatActivity {
    private static final String TAG = "Additional Info";
    //Initialise database helper
    DbHelper mDbHelper;
    //Initialise all of the widget items
    Toolbar toolbar;
    ImageView logo;
    TextView id;
    TextView name;
    TextView symbol;
    TextView rank;
    TextView price_usd;
    TextView price_btc;
    TextView volume_usd;
    TextView market_cap_usd;
    TextView available_supply;
    TextView total_supply;
    TextView max_supply;
    TextView percent_change_1h;
    TextView percent_change_24h;
    TextView percent_change_7d;
    TextView last_updated;
    ImageButton back;


    String nameS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_information);

        mDbHelper = new DbHelper(this);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        logo = (ImageView) findViewById(R.id.logo);
        id =  (TextView) findViewById(R.id.id);
        name =  (TextView) findViewById(R.id.name);
        symbol =  (TextView) findViewById(R.id.symbol);
        rank =  (TextView) findViewById(R.id.rank);
        price_usd =  (TextView) findViewById(R.id.price_usd);
        price_btc =  (TextView) findViewById(R.id.price_btc);
        volume_usd =  (TextView) findViewById(R.id.volume_usd);
        market_cap_usd =  (TextView) findViewById(R.id.market_cap_usd);
        available_supply =  (TextView) findViewById(R.id.available_supply);
        total_supply =  (TextView) findViewById(R.id.total_supply);
        max_supply = (TextView) findViewById(R.id.max_supply);
        percent_change_1h =  (TextView) findViewById(R.id.percent_change_1h);
        percent_change_24h =  (TextView) findViewById(R.id.percent_change_24h);
        percent_change_7d =  (TextView) findViewById(R.id.percent_change_7d);
        last_updated =  (TextView) findViewById(R.id.last_updated);

        back = (ImageButton) findViewById(R.id.back);



        //Attempt to access the Name which was passed as an intent from Activity Main
        try {
            Bundle bundle = getIntent().getExtras();
            if(bundle != null){
                final String name = bundle.getString("Name");
                nameS = name;
                fillData(nameS);
            }
        }catch (Exception e){
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }

back.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View view) {
        finish();
        return false;
    }
});

        name.setText("Name: " + nameS);


    }


    // Get all the coin information about a coin using the name as a key
    private void fillData(String name){
        Cursor data = mDbHelper.getAll(name);

        data.moveToFirst();

        int idIndex = data.getColumnIndexOrThrow("id");
        int idSymbol = data.getColumnIndexOrThrow("symbol");
        int idRank = data.getColumnIndexOrThrow("rank");
        int idPriceUSD = data.getColumnIndexOrThrow("price_usd");
        int idPriceBTC = data.getColumnIndexOrThrow("price_btc");
        int idVolume = data.getColumnIndexOrThrow("volume");
        int idMarketCap = data.getColumnIndexOrThrow("market_cap_usd");
        int idAvailableSupply = data.getColumnIndexOrThrow("available_supply");
        int idTotalSupply = data.getColumnIndexOrThrow("total_supply");
        int idMaxSupply = data.getColumnIndexOrThrow("max_supply");
        int idChange1h = data.getColumnIndexOrThrow("percent_change_1h");
        int idChange24h = data.getColumnIndexOrThrow("percent_change_24h");
        int idChange7d = data.getColumnIndexOrThrow("percent_change_7d");
        int idUpdated = data.getColumnIndexOrThrow("last_updated");


        String idS = data.getString(idIndex);
        String symbolS = data.getString(idSymbol);
        String rankS = data.getString(idRank);
        String priceUSDS = data.getString(idPriceUSD);
        String priceBTCS = data.getString(idPriceBTC);
        String volumeS = data.getString(idVolume);
        String marketCapS = data.getString(idMarketCap);
        String availableSupplyS = data.getString(idAvailableSupply);
        String totalSupplyS = data.getString(idTotalSupply);
        String maxSupplyS = data.getString(idMaxSupply);
        String change1hS = data.getString(idChange1h);
        String change24hS = data.getString(idChange24h);
        String change7dS = data.getString(idChange7d);
        String updatedS = data.getString(idUpdated);

        id.setText("ID: " + idS);
        symbol.setText("Symbol: " + symbolS);
        rank.setText("Rank: " +rankS);
        price_usd.setText("Price USD: $" + priceUSDS);
        price_btc.setText("Price BTC: " +priceBTCS);
        volume_usd.setText("24 hour volume: " + volumeS);
        market_cap_usd.setText("Market cap: " + marketCapS);
        available_supply.setText("Available supply:" + availableSupplyS);
        total_supply.setText("Total supply" + totalSupplyS);
        max_supply.setText("Max supply " + maxSupplyS);
        percent_change_1h.setText("Change in past hour: %" + change1hS);
        percent_change_24h.setText("Change in past 24 hours: %" + change24hS);
        percent_change_7d.setText("Change in past 7 days: %" + change7dS);
        last_updated.setText("Last updated: " +updatedS);





    }

}
