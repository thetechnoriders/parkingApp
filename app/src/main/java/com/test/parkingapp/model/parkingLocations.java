package com.test.parkingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by w-t on 9/1/2017.
 */

public class parkingLocations implements Parcelable {

    private double latitude;
    private double longitutue;
    private String locationTitle;
    private String locationAddress;
    private String locationImageURL;
    private String price;
    private String freeParkingSpace;
    private String parkingDistance;
    private String parkingTimings;
    private String totalParkingSpace;
    private String parkingDetails;
    private String parkingDeals;


    protected parkingLocations(Parcel in) {
        latitude = in.readDouble();
        longitutue = in.readDouble();
        locationTitle = in.readString();
        locationAddress = in.readString();
        locationImageURL = in.readString();
        price = in.readString();
        freeParkingSpace = in.readString();
        parkingDistance = in.readString();
        parkingTimings = in.readString();
        totalParkingSpace = in.readString();
        parkingDetails = in.readString();
        parkingDeals = in.readString();
    }

    public static final Creator<parkingLocations> CREATOR = new Creator<parkingLocations>() {
        @Override
        public parkingLocations createFromParcel(Parcel in) {
            return new parkingLocations(in);
        }

        @Override
        public parkingLocations[] newArray(int size) {
            return new parkingLocations[size];
        }
    };

    public double getLatitude() {
        return latitude;
    }

    public double getLongitutue() {
        return longitutue;
    }

    public String getLocationTitle() {
        return locationTitle;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public String getLocationImageURL() {
        return locationImageURL;
    }

    public String getPrice() {
        return price;
    }

    public String getFreeParkingSpace() {
        return freeParkingSpace;
    }

    public String getParkingDistance() {
        return parkingDistance;
    }

    public String getParkingTimings() {
        return parkingTimings;
    }

    public String getTotalParkingSpace() {
        return totalParkingSpace;
    }

    public String getParkingDetails() {
        return parkingDetails;
    }

    public String getParkingDeals() {
        return parkingDeals;
    }

    public parkingLocations(double latitude, double longitutue, String locationTitle, String locationAddress, String locationImageURL, String price, String freeParkingSpace, String parkingDistance, String parkingTimings, String totalParkingSpace, String parkingDetails, String parkingDeals) {
        this.latitude = latitude;
        this.longitutue = longitutue;
        this.locationTitle = locationTitle;
        this.locationAddress = locationAddress;
        this.locationImageURL = locationImageURL;
        this.price = price;
        this.freeParkingSpace = freeParkingSpace;
        this.parkingDistance = parkingDistance;
        this.parkingTimings = parkingTimings;
        this.totalParkingSpace = totalParkingSpace;
        this.parkingDetails = parkingDetails;
        this.parkingDeals = parkingDeals;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitutue);
        dest.writeString(locationTitle);
        dest.writeString(locationAddress);
        dest.writeString(locationImageURL);
        dest.writeString(price);
        dest.writeString(freeParkingSpace);
        dest.writeString(parkingDistance);
        dest.writeString(parkingTimings);
        dest.writeString(totalParkingSpace);
        dest.writeString(parkingDetails);
        dest.writeString(parkingDeals);
    }
}
