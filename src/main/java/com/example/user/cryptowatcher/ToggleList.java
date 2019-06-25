package com.example.user.cryptowatcher;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import com.example.user.cryptowatcher.adapter.ToggleListAdapter;
import com.example.user.cryptowatcher.database.DbHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class ToggleList extends AppCompatActivity {

    private static final String TAG = "ToggleList";

    private ListView mListView;
    DbHelper mDbHelper;
    private ToggleListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggles);
        mListView = (ListView) findViewById(R.id.toggleList);



        mDbHelper = new DbHelper(this);

        populateListView();



    }

    private void populateListView(){

        Cursor data = mDbHelper.getToggleData();
        ArrayList<CoinToggle> coinList = new ArrayList<>();
        while(data.moveToNext()){
            populateCoin(data);
            coinList.add(populateCoin(data));
        }
        adapter = new ToggleListAdapter(this, R.layout.adapter_view_layout_toggles, coinList);
        mListView.setAdapter(adapter);
    }

    private CoinToggle populateCoin(Cursor data){
        try{
        int nameIndex = data.getColumnIndexOrThrow("name");
        int idIndex = data.getColumnIndexOrThrow("id");
        int enabledIndex = data.getColumnIndex("enabled");
        String name = data.getString(nameIndex);
        String id = data.getString(idIndex);
        int enabled = data.getInt(enabledIndex);
        int imageID = R.drawable.class.getField(id).getInt(null);
        String logoURL = "drawable://" + imageID;
            return new CoinToggle(name, logoURL, enabled);
        }catch (Exception e){
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return new CoinToggle("0", "0", 0);
    }


}
