package com.example.jacob.cryptowatcher.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jacob.cryptowatcher.Coin;
import com.example.jacob.cryptowatcher.CoinInformationActivity;
import com.example.jacob.cryptowatcher.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;


/**
 * Created by Jacob on 24/03/2018.
 */

public class CoinListAdapter extends ArrayAdapter<Coin> {

    private static final String TAG = "CoinListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    //Holds all of the widget items for each list item
    static class ViewHolder {
        TextView name;
        TextView symbol;
        ImageView logo;
        TextView priceUSD;
    }

    //Inherits the resources and context from Activity Main
    public CoinListAdapter(Context context, int resource, ArrayList<Coin> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //setup the image loader
        setupImageLoader();

        //get the coin information
        final String name = getItem(position).getName();
        final String symbol = getItem(position).getSymbol();
        final String logoURL = getItem(position).getLogoURL();
        final float priceUSD = getItem(position).getPriceUSD();



        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;

        //if the view doesn't already exist, create it
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.coinName);
            holder.symbol = (TextView) convertView.findViewById(R.id.coinSymbol);
            holder.logo = (ImageView) convertView.findViewById(R.id.logo);
            holder.priceUSD = (TextView) convertView.findViewById(R.id.currentPriceUSD);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        //scrolling animation for when scrolling up and down the list
        Animation animation = AnimationUtils.loadAnimation(mContext,
        (position > lastPosition) ? R.anim.loading_down_anim : R.anim.loading_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        ImageLoader imageLoader = ImageLoader.getInstance();
        //If there's no image for a coin, set it to Bitcoin
        int defaultImage = mContext.getResources().getIdentifier("@drawable/bitcoin",null,mContext.getPackageName());

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage).build();

        imageLoader.displayImage(logoURL, holder.logo, options);


        holder.name.setText(name);
        holder.symbol.setText(symbol);
        holder.priceUSD.setText(String.valueOf("$" + priceUSD));

        //When clicking on a list item, create a new activity and pass the name to it
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CoinInformationActivity.class);
                intent.putExtra("Name", name);
                mContext.startActivity(intent);


            }
        });



        return convertView;
    }

    private void setupImageLoader(){

        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP

    }



}
