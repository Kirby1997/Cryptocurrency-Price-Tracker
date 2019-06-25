package com.example.user.cryptowatcher;

import android.content.Context;
import android.content.Intent;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.user.cryptowatcher.adapter.CoinListAdapter;
import com.example.user.cryptowatcher.database.DbHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;




import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ListView mListView;
    private DbHelper mDbHelper;
    private UpdateJSON updateJSON;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new DbHelper(this);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.mainList);
        updateJSON = new UpdateJSON(this);
        populateListView();





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.toggleCryptos);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openToggles();
            }
        });

        //banner ad
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //Sets my phone as a test device so test adds SHOULD be shown
                .addTestDevice("TESTDEVICEID")
                .build();
        mAdView.loadAd(adRequest);
    }


    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Reset up the list view so prices are up to date
        populateListView();
    }

    //Go to the ToggleList activity where coins can be enabled and disabled
    public void openToggles(){
        Intent intent = new Intent(this, ToggleList.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent  = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_update) {
            updateJSON.onCreate();
            Toast.makeText(this, "Prices updated", Toast.LENGTH_SHORT).show();
            try {//The prices update too quickly and this delay helps stop the user needing to update prices twice
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            populateListView();

            return true;
        }



        return super.onOptionsItemSelected(item);
    }



    private void populateListView(){
        // Get coin data for each enabled coin and add the coins to an object array
        Cursor data = mDbHelper.getCoinData();
        ArrayList<Coin> coinList = new ArrayList<>();
        while(data.moveToNext()){
            populateCoin(data);
            coinList.add(populateCoin(data));
        }
        CoinListAdapter adapter = new CoinListAdapter(this, R.layout.adapter_view_layout, coinList);
        mListView.setAdapter(adapter);



    }

    private Coin populateCoin(Cursor data){
        try{
            int nameIndex = data.getColumnIndexOrThrow("name");
            int symbolIndex = data.getColumnIndexOrThrow("symbol");
            int idIndex = data.getColumnIndexOrThrow("id");
            int priceUSDIndex = data.getColumnIndex("price_usd");
            String name = data.getString(nameIndex);
            String symbol = data.getString(symbolIndex);
            String id = data.getString(idIndex);
            float priceUSD = data.getFloat(priceUSDIndex);
            int imageID = R.drawable.class.getField(id).getInt(null);
            String logoURL = "drawable://" + imageID;
            return new Coin(name, symbol, logoURL, priceUSD);
        }catch (Exception e){
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        //To stop null reference exceptions happen, ensure a coin with all parameters are set
        return new Coin("0", "0", "0", 0);
    }




}
