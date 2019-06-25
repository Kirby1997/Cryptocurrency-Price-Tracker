package com.example.user.cryptowatcher.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.cryptowatcher.CoinInformationActivity;
import com.example.user.cryptowatcher.CoinToggle;
import com.example.user.cryptowatcher.MainActivity;
import com.example.user.cryptowatcher.R;
import com.example.user.cryptowatcher.database.DbHelper;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.apache.commons.lang3.BooleanUtils;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Created by user on 24/03/2018.
 */

public class ToggleListAdapter extends ArrayAdapter<CoinToggle> {

    private static final String TAG = "CoinListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    DbHelper mDbHelper;

    static class ViewHolder {
        TextView name;
        ImageView logo;
        Switch toggle;
    }



    public ToggleListAdapter(Context context, int resource, ArrayList<CoinToggle> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        mDbHelper = new DbHelper(mContext);
        //setup the image loader
        setupImageLoader();

        //get the coin information
        final String name = Objects.requireNonNull(getItem(position)).getName();
        String logoURL = Objects.requireNonNull(getItem(position)).getLogoURL();
        final int enabled = Objects.requireNonNull(getItem(position)).getEnabled();

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        final ViewHolder holder;




        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.coinName);
            holder.logo = (ImageView) convertView.findViewById(R.id.logo);
            holder.toggle = (Switch) convertView.findViewById(R.id.mSwitch);



            holder.toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                    // If a coin has just been disabled
                    if (holder.toggle.isChecked() == false) {
                        //Display a popup
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);

                        builder.setTitle("Track " + name)
                                .setMessage("Are you sure you want to stop tracking " + name + "?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Change the state in the database so coin is no longer tracked
                                        holder.toggle.setChecked(false);
                                        int newState = 0;
                                        int oldState = 1;
                                        mDbHelper.setEnabledState(name, newState, oldState);
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        holder.toggle.setChecked(true);
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }else{//reenable the switch and keep the enabled state the way it was
                        int newState = 1;
                        int oldState = 0;
                        mDbHelper.setEnabledState(name, newState, oldState);

                    }
                }

            });

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext,
        (position > lastPosition) ? R.anim.loading_down_anim : R.anim.loading_up_anim);
        result.startAnimation(animation);
        lastPosition = position;
        ImageLoader imageLoader = ImageLoader.getInstance();

        int defaultImage = mContext.getResources().getIdentifier("@drawable/bitcoin",null,mContext.getPackageName());

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage).build();

        imageLoader.displayImage(logoURL, holder.logo, options);


        holder.toggle.setChecked(BooleanUtils.toBoolean(enabled));
        holder.name.setText(name);

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
