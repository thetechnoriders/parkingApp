package com.test.parkingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.test.parkingapp.R;
import com.test.parkingapp.fragments.MainFragment;
import com.test.parkingapp.holders.LocationViewHolder;
import com.test.parkingapp.model.parkingLocations;

import java.util.ArrayList;

/**
 * Created by w-t on 9/4/2017.
 */

public class LocationsAdapter extends RecyclerView.Adapter<LocationViewHolder> {

    private ArrayList<parkingLocations> locations = new ArrayList<>();

    public LocationsAdapter(ArrayList<parkingLocations> locations){

        this.locations = locations;

    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_location, parent, false);

        return new LocationViewHolder(card);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {

        final parkingLocations location = locations.get(position);
        holder.UpdateUI(location);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.v("maps", "item clicked");

                LatLng latlng = new LatLng(location.getLatitude(),location.getLongitutue());

                MainFragment.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 13));



            }
        });

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}
