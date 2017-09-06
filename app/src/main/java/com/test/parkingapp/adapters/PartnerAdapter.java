package com.test.parkingapp.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.parkingapp.R;
import com.test.parkingapp.activities.PartnerActivity;
import com.test.parkingapp.fragments.PartnerDetailFragment;
import com.test.parkingapp.holders.PartnerViewHolder;
import com.test.parkingapp.model.parkingLocations;

import java.util.ArrayList;

/**
 * Created by w-t on 9/5/2017.
 */

public class PartnerAdapter extends RecyclerView.Adapter<PartnerViewHolder> {

    private ArrayList<parkingLocations> locations = new ArrayList<>();
    private PartnerDetailFragment mFragment;
    private Bundle mBundle;
    private Object mContext;

    public PartnerAdapter(ArrayList<parkingLocations> locations){

        this.locations = locations;

    }

    @Override
    public PartnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_partners, parent, false);

        mContext = parent.getContext();
        return new PartnerViewHolder(card);
    }

    @Override
    public void onBindViewHolder(PartnerViewHolder holder, int position) {

        final parkingLocations location = locations.get(position);
        holder.UpdateUI(location);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("maps", "item clicked: " + location.getLocationTitle());

                fragmentJump(location);



            }
        });

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    private void fragmentJump(parkingLocations location) {
        mFragment = new PartnerDetailFragment();
        mBundle = new Bundle();
        mBundle.putParcelable("loc", location);
        mFragment.setArguments(mBundle);
        switchContent(R.id.container_main_partner, mFragment);
    }

    public void switchContent(int id, Fragment fragment) {
        if (mContext == null)
            return;
        if (mContext instanceof PartnerActivity) {
            PartnerActivity mainActivity = (PartnerActivity) mContext;
            Fragment frag = fragment;
            mainActivity.switchContent(id, frag);
        }

    }
}
