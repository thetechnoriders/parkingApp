package com.test.parkingapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.test.parkingapp.R;
import com.test.parkingapp.adapters.LocationsAdapter;
import com.test.parkingapp.model.parkingLocations;

import java.util.ArrayList;


public class LocationListFragment extends Fragment{

    public static ArrayList<parkingLocations> locations = new ArrayList<>();

    public LocationListFragment() {
        // Required empty public constructor
    }


    public static LocationListFragment newInstance(ArrayList<parkingLocations> locs) {
        LocationListFragment fragment = new LocationListFragment();
        locations = locs;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location_list, container, false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recylcer_locations);
        recyclerView.setHasFixedSize(true);


        LocationsAdapter adapter = new LocationsAdapter(locations);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }

}
