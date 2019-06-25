package com.example.user.cryptowatcher.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.user.cryptowatcher.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.example.user.cryptowatcher.database.DbContract.CoinEntry.COLUMN_ENABLED;
import static com.example.user.cryptowatcher.database.DbContract.CoinEntry.COLUMN_ID;
import static com.example.user.cryptowatcher.database.DbContract.CoinEntry.COLUMN_NAME;
import static com.example.user.cryptowatcher.database.DbContract.CoinEntry.COLUMN_PRICEUSD;
import static com.example.user.cryptowatcher.database.DbContract.CoinEntry.COLUMN_SYMBOL;
import static com.example.user.cryptowatcher.database.DbContract.CoinEntry.TABLE_NAME;

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = DbHelper.class.getSimpleName();

    private Resources mResources;
    private static final String DATABASE_NAME = "coins.db";
    private static final int DATABASE_VERSION = 1;
    Context context;
    SQLiteDatabase db;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d(TAG, "Initialise");
        mResources = context.getResources();
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(TAG, "onCreate");
        //Ensures table exists
        final String COINS_TABLE_CREATE = "CREATE TABLE " + DbContract.CoinEntry.TABLE_NAME + " (" +
                DbContract.CoinEntry.IDD + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ID + " TEXT UNIQUE NOT NULL, " +
                DbContract.CoinEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL, "+
                DbContract.CoinEntry.COLUMN_SYMBOL + " TEXT NOT NULL, " +
                DbContract.CoinEntry.COLUMN_RANK + " INTEGER NOT NULL, " +
                DbContract.CoinEntry.COLUMN_PRICEUSD + " REAL, " +
                DbContract.CoinEntry.COLUMN_PRICEBTC + " REAL, " +
                DbContract.CoinEntry.COLUMN_VOLUME + " REAL, " +
                DbContract.CoinEntry.COLUMN_MARKETCAP + " REAL, " +
                DbContract.CoinEntry.COLUMN_AVAILABLESUPPLY + " REAL, " +
                DbContract.CoinEntry.COLUMN_TOTALSUPPLY + " REAL, " +
                DbContract.CoinEntry.COLUMN_MAXSUPPLY + " REAL, " +
                DbContract.CoinEntry.COLUMN_CHANGE1H + " REAL, " +
                DbContract.CoinEntry.COLUMN_CHANGE24H + " REAL, " +
                DbContract.CoinEntry.COLUMN_CHANGE7D + " REAL, " +
                DbContract.CoinEntry.COLUMN_UPDATED + " INTEGER, " +
                DbContract.CoinEntry.COLUMN_ENABLED + " INTEGER DEFAULT 0);";

        db.execSQL(COINS_TABLE_CREATE);
        Log.d(TAG, "Database Created Successfully");


        try {
            readDataToDB(db);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    //Delete the old database if new columns are added
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    //Fill database with values from JSON file to initialise
    private void readDataToDB(SQLiteDatabase db) throws IOException, JSONException {


        final String COIN_ID = "id";
        final String COIN_NAME = "name";
        final String COIN_SYMBOL = "symbol";
        final String COIN_RANK = "rank";
        final String COIN_PRICEUSD = "price_usd";
        final String COIN_PRICEBTC = "price_btc";
        final String COIN_VOLUME = "24h_volume_usd";
        final String COIN_MARKETCAP = "market_cap_usd";
        final String COIN_AVAILABLESUPPLY = "available_supply";
        final String COIN_TOTALSUPPLY = "total_supply";
        final String COIN_MAXSUPPLY = "max_supply";
        final String COIN_CHANGE1H = "percent_change_1h";
        final String COIN_CHANGE24H = "percent_change_24h";
        final String COIN_CHANGE7D = "percent_change_7d";
        final String COIN_UPDATED = "last_updated";

        try{
            String jsonDataString = readJsonDataFromFile();
            JSONArray coinJsonArray = new JSONArray(jsonDataString);

            for (int i = 0; i < coinJsonArray.length(); ++i){
                String id;
                String name;
                String symbol;
                String rank;
                String price_usd;
                String price_btc;
                String volume_usd;
                String market_cap;
                String available_supply;
                String total_supply;
                String max_supply;
                String percent_change_1h;
                String percent_change_24h;
                String percent_change_7d;
                String last_updated;

                JSONObject coinItemObject = coinJsonArray.getJSONObject(i);

                id = coinItemObject.getString(COIN_ID);
                name = coinItemObject.getString(COIN_NAME);
                symbol = coinItemObject.getString(COIN_SYMBOL);
                rank = coinItemObject.getString(COIN_RANK);
                price_usd = coinItemObject.getString(COIN_PRICEUSD);
                price_btc = coinItemObject.getString(COIN_PRICEBTC);
                volume_usd = coinItemObject.getString(COIN_VOLUME);
                market_cap = coinItemObject.getString(COIN_MARKETCAP);
                available_supply = coinItemObject.getString(COIN_AVAILABLESUPPLY);
                total_supply = coinItemObject.getString(COIN_TOTALSUPPLY);
                max_supply = coinItemObject.getString(COIN_MAXSUPPLY);
                percent_change_1h = coinItemObject.getString(COIN_CHANGE1H);
                percent_change_24h = coinItemObject.getString(COIN_CHANGE24H);
                percent_change_7d = coinItemObject.getString(COIN_CHANGE7D);
                last_updated = coinItemObject.getString(COIN_UPDATED);

                ContentValues coinValues = new ContentValues();

                coinValues.put(COLUMN_ID, id);
                coinValues.put(DbContract.CoinEntry.COLUMN_NAME, name);
                coinValues.put(DbContract.CoinEntry.COLUMN_SYMBOL, symbol);
                coinValues.put(DbContract.CoinEntry.COLUMN_RANK, rank);
                coinValues.put(DbContract.CoinEntry.COLUMN_PRICEUSD, price_usd);
                coinValues.put(DbContract.CoinEntry.COLUMN_PRICEBTC, price_btc);
                coinValues.put(DbContract.CoinEntry.COLUMN_VOLUME, volume_usd);
                coinValues.put(DbContract.CoinEntry.COLUMN_MARKETCAP, market_cap);
                coinValues.put(DbContract.CoinEntry.COLUMN_AVAILABLESUPPLY, available_supply);
                coinValues.put(DbContract.CoinEntry.COLUMN_TOTALSUPPLY, total_supply);
                coinValues.put(DbContract.CoinEntry.COLUMN_MAXSUPPLY, max_supply);
                coinValues.put(DbContract.CoinEntry.COLUMN_CHANGE1H, percent_change_1h);
                coinValues.put(DbContract.CoinEntry.COLUMN_CHANGE24H, percent_change_24h);
                coinValues.put(DbContract.CoinEntry.COLUMN_CHANGE7D, percent_change_7d);
                coinValues.put(DbContract.CoinEntry.COLUMN_UPDATED, last_updated);

                db.insert(DbContract.CoinEntry.TABLE_NAME, null, coinValues);

                Log.d(TAG, "Inserted Successfully " + coinValues);
            }
        } catch (JSONException e){
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }


    //Get all the data from JSON File
    private String readJsonDataFromFile() throws IOException {
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonDataString = null;
            inputStream = mResources.openRawResource(R.raw.coins10);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            // Go through each line of the file and get the JSON
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        }finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
         return new String(builder);

    }

    /**
     * Updates the enabled state of the coin
     * @param name
     * @param newState
     * @param oldState
     */

    public void setEnabledState(String name, int newState, int oldState){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_ENABLED +
                " = '" + newState + "' WHERE " + COLUMN_NAME + " ='" + name + "'" +
                " AND " + COLUMN_ENABLED + " = '" + oldState + "'";
        Log.d(TAG, "Enable or Disable: query: " + query);
        Log.d(TAG, "Enable or Disable: Setting enabled to " + newState);
        db.execSQL(query);
    }

    public void setPrice(String name, float price_usd){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_PRICEUSD +
                " = '" + price_usd + "' WHERE " + COLUMN_NAME + "= '" + name + "'" +
                " AND " + COLUMN_ENABLED + " = " + 1;
        Log.d(TAG, "Setting the price of coin query: " + query);
        Log.d(TAG, "Setting price to " + price_usd);
        db.execSQL(query);
    }


    public Cursor getCoinData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME +
                ", " + COLUMN_PRICEUSD + ", " + COLUMN_SYMBOL +
                " FROM " + TABLE_NAME + " WHERE "
                + COLUMN_ENABLED + " = " + 1;
        Log.d(TAG, "GetCoinData: query: " + query);
        Cursor data = db.rawQuery(query, null);
        return data;

    }

    public Cursor getAll(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + "*" +
                " FROM " + TABLE_NAME + " WHERE "
                + COLUMN_NAME + " = '" + name + "'";

        Cursor data = db.rawQuery(query, null);
        Log.d(TAG, "GetCoinData: query: " + query);
        return data;

    }


    public Cursor getToggleData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME +
                ", " + COLUMN_ENABLED + " FROM " + TABLE_NAME;
        Log.d(TAG, "GetToggleData: query: " + query);
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_NAME + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
