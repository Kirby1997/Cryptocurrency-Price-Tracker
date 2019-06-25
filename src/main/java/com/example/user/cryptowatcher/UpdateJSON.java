package com.example.jacob.cryptowatcher;


import android.content.Intent;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.example.jacob.cryptowatcher.database.DbContract;
import com.example.jacob.cryptowatcher.database.DbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;


public class UpdateJSON extends AppCompatActivity {

    private Resources mResources;
    String link;
    SQLiteDatabase db;
    DbHelper mDbHelper;
    Context mContext;


    public UpdateJSON(Context context){
        mResources = context.getResources();
        mContext = context;
    }


    protected void onCreate() {




        mDbHelper = new DbHelper(mContext);
        //Link for the top 10 coins on coin market cap
        link = "https://api.coinmarketcap.com/v1/ticker/?limit=10";
        new JSONTask().execute(link);


    }





    @SuppressLint("StaticFieldLeak")
    public class JSONTask extends AsyncTask<String, String, String> {


        //Download the JSON in a background thread so not to slow down UI thread if request is large
        @Override
        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;
            StringBuffer buffer = null;



            try {
                // set the url as the input buffer
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                buffer = new StringBuffer();

                //Go through every line of the request and add it to the buffer
                String line;
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                //Update the prices in the database for the ones with names in it
                String finalJSON = buffer.toString();
                JSONArray jsonArray = new JSONArray(finalJSON);
                final String COIN_PRICEUSD = "price_usd";
                final String COIN_NAME = "name";
                for (int i = 0; i < jsonArray.length(); i++){
                    String price_usd;
                    String name;
                    JSONObject coinItemObject = jsonArray.getJSONObject(i);
                    price_usd = coinItemObject.getString(COIN_PRICEUSD);
                    name = coinItemObject.getString(COIN_NAME);
                    ContentValues coinValues = new ContentValues();
                    coinValues.put(DbContract.CoinEntry.COLUMN_PRICEUSD, price_usd);
                    coinValues.put(DbContract.CoinEntry.COLUMN_NAME, name);

                    mDbHelper.setPrice(name, Float.parseFloat(price_usd));
                }


            } catch (IOException | JSONException e){
                e.printStackTrace();
            } finally{
                if(connection != null){
                    connection.disconnect();
                }

                try {
                    if (reader != null){
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }






}

}
