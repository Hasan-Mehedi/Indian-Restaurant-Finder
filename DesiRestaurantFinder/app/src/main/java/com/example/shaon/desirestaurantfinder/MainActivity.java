package com.example.shaon.desirestaurantfinder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    EditText zipCode;
    double lat, lng;

    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // searchTerm = (EditText) findViewById(R.id.searchTerm);
        zipCode = (EditText) findViewById(R.id.zipCode);
    }

    public void search(View view) {

        final Geocoder geocoder = new Geocoder(this);
        final String zip = zipCode.getText().toString();
        try {
            List<Address> addresses = geocoder.getFromLocationName(zip, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                // Use the address as needed
//                String message = String.format("Latitude: %f, Longitude: %f",
//                        address.getLatitude(), address.getLongitude());
                lat = address.getLatitude();
                lng = address.getLongitude();

            } else {
                // Display appropriate message when Geocoder services are not available
                Toast.makeText(this, "Unable to geocode zipcode", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            // handle exception
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
            Toast.makeText(this, "Cant get Current location", Toast.LENGTH_LONG).show();
        } else {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                Address address = addresses.get(0);
                lat = address.getLatitude();
                lng = address.getLongitude();
                //Toast.makeText(this,"this is lattitude" + lat,Toast.LENGTH_LONG).show();
                //zipCode.setText(address.getPostalCode());
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
}
