package com.example.shaon.desirestaurantfinder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, AdapterView.OnItemClickListener {

    EditText zipCode;
    double lat, lng;

    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;

    ViewPager mViewPager;
    CustomPagerAdapter mCustomPagerAdapter;
    Timer timer;
    int page = 1;

    DrawerLayout drawerLayout;
    ListView listView;
    String[] options;
    ActionBarDrawerToggle drawerListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zipCode = (EditText) findViewById(R.id.zipCode);
        //welcome screen as a dialog
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.welcome_screen, (ViewGroup) findViewById(R.id.welcome));
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

        //navigation drawer for setting different options
        options = getResources().getStringArray(R.array.option);
        listView = (ListView) findViewById(R.id.drawerList);
        listView.setOnItemClickListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };


        //viewpager to slide show image
        mCustomPagerAdapter = new CustomPagerAdapter(this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
        pageSwitcher(3);
    }


    public void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
        // in
        // milliseconds
    }


    // this is an inner class...
    class RemindTask extends TimerTask {

        @Override
        public void run() {

            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            runOnUiThread(new Runnable() {
                public void run() {

                    if (page > 5) {
                        page = 1;
                    }
                    mViewPager.setCurrentItem(page++);

                }
            });

        }
    }


    public void search(View view) {

        final Geocoder geocoder = new Geocoder(this);
        final String zip = zipCode.getText().toString();
        try {
            List<Address> addresses = geocoder.getFromLocationName(zip, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                lat = address.getLatitude();
                lng = address.getLongitude();

            } else {
                // Display appropriate message when Geocoder services are not available
                Toast.makeText(this, "Unable to search zipcode", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {

        }

        Intent intent = new Intent(this, ResultsActivity.class);
        Bundle b = new Bundle();
        b.putDouble("lattitude", lat);
        b.putDouble("longitude", lng);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void getCurrentLocation(View view) {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null) {
            Toast.makeText(this, "Can't get Current location", Toast.LENGTH_LONG).show();
        } else {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                Address address = addresses.get(0);
                lat = address.getLatitude();
                lng = address.getLongitude();
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                Intent intent = new Intent(this, ResultsActivity.class);
                Bundle b = new Bundle();
                b.putDouble("lattitude", lat);
                b.putDouble("longitude", lng);
                intent.putExtras(b);
                startActivity(intent);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listView.setItemChecked(position, true);

    }
}
