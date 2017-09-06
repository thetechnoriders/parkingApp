package com.test.parkingapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.test.parkingapp.R;
import com.test.parkingapp.adapters.PartnerAdapter;
import com.test.parkingapp.model.parkingLocations;

import java.util.ArrayList;


public class PartnerListFragment extends Fragment {

    public static ArrayList<parkingLocations> partners = new ArrayList<>();



    public PartnerListFragment() {
        // Required empty public constructor
    }


    public static PartnerListFragment newInstance(ArrayList<parkingLocations> partnerslist) {
        PartnerListFragment fragment = new PartnerListFragment();
        partners = partnerslist;
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
        View view = inflater.inflate(R.layout.fragment_partner_list, container, false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recylcer_partners);
        recyclerView.setHasFixedSize(true);


        PartnerAdapter adapter = new PartnerAdapter(partners);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);


        return view;
    }

}
