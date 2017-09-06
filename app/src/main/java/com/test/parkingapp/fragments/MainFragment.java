package com.test.parkingapp.fragments;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.test.parkingapp.R;
import com.test.parkingapp.interfaces.locationCallback;
import com.test.parkingapp.model.parkingLocations;
import com.test.parkingapp.services.DataService;


import java.util.ArrayList;


public class MainFragment extends Fragment implements OnMapReadyCallback {


    public static GoogleMap mMap;
    private MarkerOptions userMarker;
    private LocationListFragment mLocationListFragment;
    private ArrayList<parkingLocations> locations = new ArrayList<>();





    public MainFragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();

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
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


        return view;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.getUiSettings().setMapToolbarEnabled(false);


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                //Log.v("maps","marker clicked");

                hideList();

                try {

                    if(marker.getTag().toString() != null){

                        Log.v("maps", marker.getTag().toString());

                        final parkingLocations loc = locations.get(Integer.parseInt(marker.getTag().toString()));

                        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                            @Override
                            public View getInfoWindow(Marker marker) {
                                return null;
                            }

                            @Override
                            public View getInfoContents(Marker marker) {

                                // Getting view from the layout file info_window_layout
                                View v = getActivity().getLayoutInflater().inflate(R.layout.info_window_layout, null);

                                TextView tvTitle = (TextView) v.findViewById(R.id.tx_title);
                                TextView tvAddress = (TextView) v.findViewById(R.id.tx_address);
                                TextView tvPrice = (TextView) v.findViewById(R.id.tx_price);
                                TextView tvFreeSpace = (TextView) v.findViewById(R.id.tx_free);
                                TextView tvDistance = (TextView) v.findViewById(R.id.tx_distance);
                                TextView tvTimings = (TextView) v.findViewById(R.id.tx_timings);
                                ImageView imageView = (ImageView) v.findViewById(R.id.img);


                                tvTitle.setText(loc.getLocationTitle());
                                tvAddress.setText(loc.getLocationAddress());
                                tvPrice.setText("Price  €" + loc.getPrice() + "/Hour");
                                tvFreeSpace.setText("Free  " + loc.getFreeParkingSpace());
                                tvDistance.setText("Distance  " + loc.getParkingDistance());
                                tvTimings.setText("Timings  " + loc.getParkingTimings());


                                String uri = loc.getLocationImageURL().replace(" ","%20");


                                ImageLoader imageLoader = ImageLoader.getInstance();
                                DisplayImageOptions options = new DisplayImageOptions.Builder()
                                        .cacheInMemory(true)
                                        .resetViewBeforeLoading(true)
                                        .cacheOnDisk(true)
                                        .build();

                                //download and display image from url
                                imageLoader.displayImage(uri, imageView);


                                return v;
                            }
                        });


                    }

                }catch(Exception ex){

                    }



                return false;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                showList();
            }
        });

    }



    public void setMarkers(LatLng latLng,int type, boolean isCalledFromMenu){

        if(userMarker == null){
            userMarker = new MarkerOptions().position(latLng).title("Current Location");
            mMap.addMarker(userMarker);
        }

        if(type == 1)
            updateMapFromAPI(latLng, isCalledFromMenu);

        else if(type == 2)
            updateMapFromAPIByPrice(latLng, isCalledFromMenu);

        else if(type == 3)
            updateMapFromAPIByAvailableSpace(latLng, isCalledFromMenu);

        else if(type == 4)
            updateMapFromGoogle(latLng, isCalledFromMenu);


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
    }

    private void updateMapFromGoogle(LatLng currentLocation, final boolean isCalled){


        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Fetching all parkings");
        pd.show();

        locations = DataService.getInstance().getParkingPlaces(currentLocation, getActivity(), new locationCallback() {
            @Override
            public void locationCallBack(boolean status) {


                if(status){

                    for (int i=0; i<locations.size();i++){

                        parkingLocations location = locations.get(i);
                        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
                        Bitmap bmp = Bitmap.createBitmap(150, 180, conf);
                        Canvas canvas1 = new Canvas(bmp);

                        // paint defines the text color, stroke width and size
                        Paint color = new Paint();
                        color.setTextSize(40);
                        color.setColor(Color.BLACK);

                        // modify canvas
                        canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
                                R.drawable.icon_marker_50), 0,0, color);
                        //canvas1.scale(80,80);
                        canvas1.drawText("", 60, 65, color);

                        MarkerOptions marker = new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitutue()));
                        marker.title(location.getLocationTitle());
                        marker.snippet(location.getLocationAddress());
                        marker.icon(BitmapDescriptorFactory.fromBitmap(bmp));

                        //Marker marker_org = mMap.addMarker(marker);

                        //marker_org.setTag(i);
                        mMap.addMarker(marker);
                    }

                    mLocationListFragment = (LocationListFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.container_locations_list);


                    if(isCalled){


                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

                        fragmentTransaction.remove(mLocationListFragment).commit();

                        mLocationListFragment = LocationListFragment.newInstance(locations);

                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_locations_list, mLocationListFragment).commit();

                    }

                    //mLocationListFragment = (LocationListFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.container_locations_list);

                    if(mLocationListFragment == null){

                        mLocationListFragment = LocationListFragment.newInstance(locations);

                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_locations_list, mLocationListFragment).commit();
                    }

                    pd.dismiss();

                }
                else
                {
                    Toast.makeText(getActivity(),"Cannot fetch parkings, please check your internet connection",Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }

            }
        });

    }

    private void updateMapFromAPI(LatLng currentLocation, final boolean isCalled){


        mMap.clear();

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Fetching all parkings");
        pd.show();

        locations = DataService.getInstance().getParkingPlacesAPI(currentLocation, getActivity(), new locationCallback() {
            @Override
            public void locationCallBack(boolean status) {


                if(status){

                    for (int i=0; i<locations.size();i++){

                        final parkingLocations location = locations.get(i);

                        if(i == 0)
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitutue()),13));


                        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
                        Bitmap bmp = Bitmap.createBitmap(150, 180, conf);
                        Canvas canvas1 = new Canvas(bmp);

                        // paint defines the text color, stroke width and size
                        Paint color = new Paint();
                        color.setTextSize(40);
                        color.setColor(Color.BLACK);
                        color.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

                        // modify canvas
                        canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
                                R.drawable.icon_marker_50), 0,0, color);
                        //canvas1.scale(80,80);
                        canvas1.drawText(location.getFreeParkingSpace(), 60, 65, color);

                        MarkerOptions marker = new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitutue()));
                        //marker.title(location.getLocationTitle());
                        //marker.snippet(location.getLocationAddress());
                        marker.icon(BitmapDescriptorFactory.fromBitmap(bmp));

                        Marker marker_org = mMap.addMarker(marker);

                        marker_org.setTag(String.valueOf(i));

                        //mMap.addMarker(marker);
                    }


                    mLocationListFragment = (LocationListFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.container_locations_list);


                    if(isCalled){


                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

                        fragmentTransaction.remove(mLocationListFragment).commit();

                        mLocationListFragment = LocationListFragment.newInstance(locations);

                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_locations_list, mLocationListFragment).commit();

                    }

                    //mLocationListFragment = null;

                    if(mLocationListFragment == null){

                        mLocationListFragment = LocationListFragment.newInstance(locations);

                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_locations_list, mLocationListFragment).commit();
                    }

                    pd.dismiss();

                }
                else
                {
                    Toast.makeText(getActivity(),"Cannot fetch parkings, please check your internet connection",Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }

            }
        });

    }

    private void updateMapFromAPIByPrice(LatLng currentLocation, final boolean isCalled){


        mMap.clear();

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Fetching all parkings");
        pd.show();

        locations = DataService.getInstance().getParkingPlacesAPIByPrice(currentLocation, getActivity(), new locationCallback() {
            @Override
            public void locationCallBack(boolean status) {


                if(status){

                    for (int i=0; i<locations.size();i++){

                        parkingLocations location = locations.get(i);

                        if(i == 0)
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitutue()),13));


                        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
                        Bitmap bmp = Bitmap.createBitmap(150, 180, conf);
                        Canvas canvas1 = new Canvas(bmp);

                        // paint defines the text color, stroke width and size
                        Paint color = new Paint();
                        color.setTextSize(40);
                        color.setColor(Color.BLACK);
                        color.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

                        // modify canvas
                        canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
                                R.drawable.icon_marker_50), 0,0, color);
                        //canvas1.scale(80,80);
                        canvas1.drawText(location.getFreeParkingSpace(), 60, 65, color);

                        MarkerOptions marker = new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitutue()));
                        //marker.title(location.getLocationTitle());
                        //marker.snippet(location.getLocationAddress());
                        marker.icon(BitmapDescriptorFactory.fromBitmap(bmp));
                        //mMap.addMarker(marker);
                        Marker marker_org = mMap.addMarker(marker);

                        marker_org.setTag(String.valueOf(i));

                    }


                    mLocationListFragment = (LocationListFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.container_locations_list);


                    if(isCalled){


                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

                        fragmentTransaction.remove(mLocationListFragment).commit();

                        mLocationListFragment = LocationListFragment.newInstance(locations);

                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_locations_list, mLocationListFragment).commit();

                    }

                    //mLocationListFragment = null;

                    if(mLocationListFragment == null){

                        mLocationListFragment = LocationListFragment.newInstance(locations);

                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_locations_list, mLocationListFragment).commit();
                    }

                    pd.dismiss();

                }
                else
                {
                    Toast.makeText(getActivity(),"Cannot fetch parkings, please check your internet connection",Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }

            }
        });

    }

    private void updateMapFromAPIByAvailableSpace(LatLng currentLocation, final boolean isCalled){


        mMap.clear();

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Fetching all parkings");
        pd.show();

        locations = DataService.getInstance().getParkingPlacesAPIByAvailableSpace(currentLocation, getActivity(), new locationCallback() {
            @Override
            public void locationCallBack(boolean status) {


                if(status){

                    for (int i=0; i<locations.size();i++){

                        parkingLocations location = locations.get(i);

                        if(i == 0)
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitutue()),13));


                        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
                        Bitmap bmp = Bitmap.createBitmap(150, 180, conf);
                        Canvas canvas1 = new Canvas(bmp);

                        // paint defines the text color, stroke width and size
                        Paint color = new Paint();
                        color.setTextSize(40);
                        color.setColor(Color.BLACK);
                        color.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

                        // modify canvas
                        canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
                                R.drawable.icon_marker_50), 0,0, color);
                        //canvas1.scale(80,80);
                        canvas1.drawText(location.getFreeParkingSpace(), 60, 65, color);

                        MarkerOptions marker = new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitutue()));
                        //marker.title(location.getLocationTitle());
                        //marker.snippet(location.getLocationAddress());
                        marker.icon(BitmapDescriptorFactory.fromBitmap(bmp));
                        //mMap.addMarker(marker);

                        Marker marker_org = mMap.addMarker(marker);

                        marker_org.setTag(String.valueOf(i));

                    }


                    mLocationListFragment = (LocationListFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.container_locations_list);


                    if(isCalled){


                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

                        fragmentTransaction.remove(mLocationListFragment).commit();

                        mLocationListFragment = LocationListFragment.newInstance(locations);

                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_locations_list, mLocationListFragment).commit();

                    }

                    //mLocationListFragment = null;

                    if(mLocationListFragment == null){

                        mLocationListFragment = LocationListFragment.newInstance(locations);

                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_locations_list, mLocationListFragment).commit();
                    }

                    pd.dismiss();

                }
                else
                {
                    Toast.makeText(getActivity(),"Cannot fetch parkings, please check your internet connection",Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }

            }
        });

    }

    public void hideList(){
        getActivity().getSupportFragmentManager().beginTransaction().hide(mLocationListFragment).commit();
    }

    public void showList(){
        getActivity().getSupportFragmentManager().beginTransaction().show(mLocationListFragment).commit();
    }



}
