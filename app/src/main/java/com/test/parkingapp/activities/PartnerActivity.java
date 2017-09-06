package com.test.parkingapp.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.test.parkingapp.R;
import com.test.parkingapp.fragments.PartnerListFragment;
import com.test.parkingapp.interfaces.locationCallback;
import com.test.parkingapp.services.DataService;

import static com.test.parkingapp.fragments.PartnerListFragment.partners;

public class PartnerActivity extends AppCompatActivity {

    private static PartnerListFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().hide();

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Fetching all partners");
        pd.show();

        partners = DataService.getInstance().getParkingPlacesAPI(MainActivity.currentLocation,this, new locationCallback() {
            @Override
            public void locationCallBack(boolean status) {

                if(status) {

                    fragment = (PartnerListFragment) getSupportFragmentManager().findFragmentById(R.id.container_main_partner);
                       if (fragment == null) {
                           fragment = fragment.newInstance(partners);
                           getSupportFragmentManager().beginTransaction().add(R.id.container_main_partner, fragment).commit();

                       }



                }
                pd.dismiss();

            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        //finish();
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 0) {
            finish();   // write your code to switch between fragments.
        } else {
            super.onBackPressed();
        }

    }

    public void switchContent(int id, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment, fragment.toString());
        ft.addToBackStack(null);
        ft.commit();
    }


}
