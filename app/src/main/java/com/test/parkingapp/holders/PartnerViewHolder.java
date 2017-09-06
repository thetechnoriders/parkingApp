package com.test.parkingapp.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.test.parkingapp.R;
import com.test.parkingapp.model.parkingLocations;

/**
 * Created by w-t on 9/5/2017.
 */

public class PartnerViewHolder extends RecyclerView.ViewHolder{

    private TextView tv_title;
    private TextView tv_address;
    private TextView tv_price;
    private TextView tv_free;
    private TextView tv_distance;
    private TextView tv_timings;
    private ImageView img_parking;

    public PartnerViewHolder(View itemView) {
        super(itemView);

        tv_title = (TextView)itemView.findViewById(R.id.tv_title);
        tv_address = (TextView)itemView.findViewById(R.id.tv_address);
        tv_price = (TextView)itemView.findViewById(R.id.tv_price);
        tv_free = (TextView)itemView.findViewById(R.id.tv_free);
        tv_distance = (TextView)itemView.findViewById(R.id.tv_distance);
        tv_timings = (TextView)itemView.findViewById(R.id.tv_timimgs);
        img_parking = (ImageView)itemView.findViewById(R.id.img_parking);

    }

    public void UpdateUI(parkingLocations location){


        tv_title.setText(location.getLocationTitle());
        tv_address.setText(location.getLocationAddress());

        if(!location.getPrice().equalsIgnoreCase(""))
            tv_price.setText("Price  €" + String.valueOf(location.getPrice()) + "/Hour");
        else
            tv_price.setVisibility(View.GONE);


        if(!location.getFreeParkingSpace().equalsIgnoreCase(""))
            tv_free.setText("Free  " + String.valueOf(location.getFreeParkingSpace()));
        else
            tv_free.setVisibility(View.GONE);


        if(!location.getParkingDistance().equalsIgnoreCase(""))
            tv_distance.setText("Distance  " + String.valueOf(location.getParkingDistance()));
        else
            tv_distance.setVisibility(View.GONE);


        if(!location.getParkingTimings().equalsIgnoreCase(""))
            tv_timings.setText("Timings:  " + String.valueOf(location.getParkingTimings()));
        else
            tv_timings.setVisibility(View.GONE);


        if(location.getLocationImageURL().equalsIgnoreCase("")) {
            img_parking.setVisibility(View.GONE);
        }
        else
        {
//            String uri = location.getLocationImageURL();
//            int resource = img_parking.getResources().getIdentifier(uri, null, img_parking.getContext().getPackageName());
//            img_parking.setImageResource(resource);

            String uri = location.getLocationImageURL().replace(" ","%20");

            ImageLoader imageLoader = ImageLoader.getInstance();
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .cacheOnDisc(true).resetViewBeforeLoading(true)
                    .showImageForEmptyUri(null)
                    .showImageOnFail(null)
                    .showImageOnLoading(null).build();

            //download and display image from url
            imageLoader.displayImage(uri, img_parking, options);
        }


    }
}
