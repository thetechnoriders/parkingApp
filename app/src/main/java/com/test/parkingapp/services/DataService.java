package com.test.parkingapp.services;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.test.parkingapp.interfaces.locationCallback;
import com.test.parkingapp.model.parkingLocations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by w-t on 9/1/2017.
 */

public class DataService {

    public static ArrayList<parkingLocations> arrayListParkingLocations;

    private static final DataService ourInstance = new DataService();

    public static DataService getInstance() {
        return ourInstance;
    }

    private DataService() {

    }

    public ArrayList<parkingLocations> getParkingPlaces(LatLng currentLocation, Context context, final locationCallback locationCallback){

        arrayListParkingLocations = new ArrayList<>();

        /*  arrayListParkingLocations.add(new parkingLocations(24.8438739,67.04124000000002,"Parking 1", "Dr Daud Pota Rd, Karachi, Pakistan", "",2));
        arrayListParkingLocations.add(new parkingLocations(24.8712159,67.33841949999999,"Parking 2", "Pakistan Steel Town, Karachi, Pakistan", "",3));
        arrayListParkingLocations.add(new parkingLocations(24.87054879999999,67.09491939999998,"Parking 3", "PAF Faisal Air Base، Karachi, Pakistan", "",4));
        */

         String currentLocCordinates = currentLocation.latitude + "," + currentLocation.longitude;

        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+ currentLocCordinates +"&radius=8000&type=parking&key=AIzaSyBUBVk8RWi1XAuEUdtoMPVONfe9hQZeBfk";

       // final ArrayList<parkingLocations> arrayListParkingLocations = new ArrayList<>();


        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray results = response.getJSONArray("results");


                    for (int i = 0; i < results.length(); i++){

                        JSONObject object = results.getJSONObject(i);

                        JSONObject geometry = object.getJSONObject("geometry");
                        JSONObject locations = geometry.getJSONObject("location");


                        double latitude = locations.getDouble("lat");
                        double longitude = locations.getDouble("lng");

                        String title = object.getString("name");
                        String address = object.getString("vicinity");

                        arrayListParkingLocations.add(new parkingLocations(latitude,longitude,title, address, "","", "", "",
                                "","","",""));

                    }

                    locationCallback.locationCallBack(true);
                    //pd.dismiss();

                }catch (JSONException ex){

                    Log.e("error","Error: " + ex.getLocalizedMessage());

                    //pd.dismiss();
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.v("nabeel","time out");
                } else if (error instanceof AuthFailureError) {
                    Log.v("nabeel","AuthFailureError");
                } else if (error instanceof ServerError) {
                    Log.v("nabeel","ServerError");
                } else if (error instanceof NetworkError) {
                    Log.v("nabeel","NetworkError");
                } else if (error instanceof ParseError) {
                    Log.v("nabeel","ParseError");
                }

                //serverCallBacks.fileUploadCallBack(false, invoiceInfo, null);
                locationCallback.locationCallBack(false);


            }
        });

        Volley.newRequestQueue(context).add(jsonObjectRequest);

        return arrayListParkingLocations;
    }

    public ArrayList<parkingLocations> getParkingPlacesAPI(LatLng currentLocation, Context context, final locationCallback locationCallback){

        arrayListParkingLocations = new ArrayList<>();

        String url = "https://real-time-parking-search-engine.000webhostapp.com/public/parkings?lat=" +currentLocation.latitude + "&lng=" +currentLocation.longitude;

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray response) {

                try {

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject object = response.getJSONObject(i);

                        String title = object.optString("name","");
                        String address = object.optString("address","");
                        String price = object.optString("price","");
                        String free = object.optString("free","");
                        String latitude = object.optString("lat","");
                        String longitude = object.optString("long","");
                        String imageUrl = object.optString("image","");
                        String timings = object.optString("timings","");
                        String distance = object.optString("distance","");
                        String deals = object.optString("deals","");
                        String total_space = object.optString("total","");
                        String description = object.optString("description","");

                        if(!distance.equalsIgnoreCase("") || distance != null){

                            double result = Double.parseDouble(distance);
                            distance = String.format("%.2f", result);

                        }


                        arrayListParkingLocations.add(new parkingLocations(Double.parseDouble(latitude), Double.parseDouble(longitude)
                        , title,address,imageUrl,price,free,distance,timings,total_space,description,deals ));


                    }


                    locationCallback.locationCallBack(true);
                    //pd.dismiss();

                }catch (Exception ex){

                    Log.e("error","Error: " + ex.getLocalizedMessage());

                    //pd.dismiss();
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.v("nabeel","time out");
                } else if (error instanceof AuthFailureError) {
                    Log.v("nabeel","AuthFailureError");
                } else if (error instanceof ServerError) {
                    Log.v("nabeel","ServerError");
                } else if (error instanceof NetworkError) {
                    Log.v("nabeel","NetworkError");
                } else if (error instanceof ParseError) {
                    Log.v("nabeel","ParseError");
                }

                //serverCallBacks.fileUploadCallBack(false, invoiceInfo, null);
                locationCallback.locationCallBack(false);


            }
        });

        Volley.newRequestQueue(context).add(jsonArrayRequest);

        return arrayListParkingLocations;
    }

    public ArrayList<parkingLocations> getParkingPlacesAPIByPrice(LatLng currentLocation, Context context, final locationCallback locationCallback){

        arrayListParkingLocations = new ArrayList<>();

        String url = "https://real-time-parking-search-engine.000webhostapp.com/public/parkings/price?lat=" +currentLocation.latitude + "&lng=" +currentLocation.longitude;

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray response) {

                try {

                    Log.v("maps","length: " + response.length());

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject object = response.getJSONObject(i);

                        String title = object.optString("name","");
                        String address = object.optString("address","");
                        String price = object.optString("price","");
                        String free = object.optString("free","");
                        String latitude = object.optString("lat","");
                        String longitude = object.optString("long","");
                        String imageUrl = object.optString("image","");
                        String timings = object.optString("timings","");
                        String distance = object.optString("distance","");
                        String deals = object.optString("deals","");
                        String total_space = object.optString("total","");
                        String description = object.optString("description","");

                        if(!distance.equalsIgnoreCase("") || distance != null){

                            double result = Double.parseDouble(distance);
                            distance = String.format("%.2f", result);

                        }


                        arrayListParkingLocations.add(new parkingLocations(Double.parseDouble(latitude), Double.parseDouble(longitude)
                                , title,address,imageUrl,price,free,distance,timings,total_space,description,deals ));


                    }


                    locationCallback.locationCallBack(true);
                    //pd.dismiss();

                }catch (Exception ex){

                    Log.e("error","Error: " + ex.getLocalizedMessage());

                    //pd.dismiss();
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.v("nabeel","time out");
                } else if (error instanceof AuthFailureError) {
                    Log.v("nabeel","AuthFailureError");
                } else if (error instanceof ServerError) {
                    Log.v("nabeel","ServerError");
                } else if (error instanceof NetworkError) {
                    Log.v("nabeel","NetworkError");
                } else if (error instanceof ParseError) {
                    Log.v("nabeel","ParseError");
                }

                //serverCallBacks.fileUploadCallBack(false, invoiceInfo, null);
                locationCallback.locationCallBack(false);


            }
        });

        Volley.newRequestQueue(context).add(jsonArrayRequest);

        return arrayListParkingLocations;
    }

    public ArrayList<parkingLocations> getParkingPlacesAPIByAvailableSpace(LatLng currentLocation, Context context, final locationCallback locationCallback){

        arrayListParkingLocations = new ArrayList<>();

        String url = "https://real-time-parking-search-engine.000webhostapp.com/public/parkings/space?lat=" +currentLocation.latitude + "&lng=" +currentLocation.longitude;

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray response) {

                try {

                    Log.v("maps","length: " + response.length());

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject object = response.getJSONObject(i);

                        String title = object.optString("name","");
                        String address = object.optString("address","");
                        String price = object.optString("price","");
                        String free = object.optString("free","");
                        String latitude = object.optString("lat","");
                        String longitude = object.optString("long","");
                        String imageUrl = object.optString("image","");
                        String timings = object.optString("timings","");
                        String distance = object.optString("distance","");
                        String deals = object.optString("deals","");
                        String total_space = object.optString("total","");
                        String description = object.optString("description","");

                        if(!distance.equalsIgnoreCase("") || distance != null){

                            double result = Double.parseDouble(distance);
                            distance = String.format("%.2f", result);

                        }
                        arrayListParkingLocations.add(new parkingLocations(Double.parseDouble(latitude), Double.parseDouble(longitude)
                                , title,address,imageUrl,price,free,distance,timings,total_space,description,deals ));

                    }


                    locationCallback.locationCallBack(true);
                    //pd.dismiss();

                }catch (Exception ex){

                    Log.e("error","Error: " + ex.getLocalizedMessage());

                    //pd.dismiss();
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.v("nabeel","time out");
                } else if (error instanceof AuthFailureError) {
                    Log.v("nabeel","AuthFailureError");
                } else if (error instanceof ServerError) {
                    Log.v("nabeel","ServerError");
                } else if (error instanceof NetworkError) {
                    Log.v("nabeel","NetworkError");
                } else if (error instanceof ParseError) {
                    Log.v("nabeel","ParseError");
                }

                //serverCallBacks.fileUploadCallBack(false, invoiceInfo, null);
                locationCallback.locationCallBack(false);


            }
        });

        Volley.newRequestQueue(context).add(jsonArrayRequest);

        return arrayListParkingLocations;
    }
}
