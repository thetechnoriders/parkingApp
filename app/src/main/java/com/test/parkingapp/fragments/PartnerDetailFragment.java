package com.test.parkingapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.test.parkingapp.R;
import com.test.parkingapp.model.parkingLocations;


public class PartnerDetailFragment extends Fragment {


    private parkingLocations location;

    public PartnerDetailFragment() {
        // Required empty public constructor
    }


    public static PartnerDetailFragment newInstance() {
        PartnerDetailFragment fragment = new PartnerDetailFragment();

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
        View view = inflater.inflate(R.layout.fragment_partner_detail, container, false);

//        Bundle bundle = getActivity().getIntent().getExtras();
//        parkingLocations location = bundle.getParcelable("loc");
//
//        Toast.makeText(getActivity(),"title: " + location.getLocationTitle(), Toast.LENGTH_LONG).show();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            location = bundle.getParcelable("loc");
            //Toast.makeText(getActivity(),"title: " + location.getLocationTitle(), Toast.LENGTH_LONG).show();
        }


        TextView tv_title = (TextView)view.findViewById(R.id.tv_title);
        TextView tv_address = (TextView)view.findViewById(R.id.tv_address);
        TextView tv_price = (TextView)view.findViewById(R.id.tv_price);
        TextView tv_parking_space = (TextView)view.findViewById(R.id.tv_parking_space);
        TextView tv_free_space = (TextView)view.findViewById(R.id.tv_free_space);
        TextView tv_timings = (TextView)view.findViewById(R.id.tv_timimgs);
        TextView tv_details = (TextView)view.findViewById(R.id.tv_details);
        TextView tv_deals = (TextView)view.findViewById(R.id.tv_deals);

        ImageView img_parking = (ImageView)view.findViewById(R.id.img_parking_space);


        if (location != null) {

            tv_title.setText(location.getLocationTitle());
            tv_address.setText(location.getLocationAddress());
            tv_title.setText(location.getLocationTitle());
            tv_price.setText("€" + location.getPrice() + "/Hour");
            tv_parking_space.setText(location.getTotalParkingSpace());
            tv_free_space.setText(location.getFreeParkingSpace());
            tv_timings.setText(location.getParkingTimings());
            tv_details.setText(location.getParkingDetails());
            tv_deals.setText(location.getParkingDeals());

            String uri = location.getLocationImageURL();
//            int resource = img_parking.getResources().getIdentifier(uri, null, img_parking.getContext().getPackageName());
//            img_parking.setImageResource(resource);


            ImageLoader imageLoader = ImageLoader.getInstance();
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .cacheOnDisc(true).resetViewBeforeLoading(true)
                    .showImageForEmptyUri(null)
                    .showImageOnFail(null)
                    .showImageOnLoading(null).build();

            //download and display image from url
            imageLoader.displayImage(uri, img_parking, options);

        }



        return view;
    }


}
