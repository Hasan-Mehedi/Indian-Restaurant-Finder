package com.example.shaon.desirestaurantfinder;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class set_location extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mgoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);

        //setting the welcome screen as a toast
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.welcome_screen, (ViewGroup) findViewById(R.id.welcome));
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

        if (googleapiavailable()) {

            initmap();
        }

    }

    private void initmap() {
        MapFragment mapFragment= (MapFragment) getFragmentManager().findFragmentById(R.id.mapfragment);
        mapFragment.getMapAsync(this);
    }

    //check google playservice availablility
   public boolean googleapiavailable() {

       GoogleApiAvailability api = GoogleApiAvailability.getInstance();
       int isavailable = api.isGooglePlayServicesAvailable(this);
       if (isavailable == ConnectionResult.SUCCESS) {
           return true;
       } else if (api.isUserResolvableError(isavailable)) {
           Dialog dialog = api.getErrorDialog(this, isavailable, 0);
           dialog.show();
       } else {
           Toast.makeText(this, "play store is not found", Toast.LENGTH_LONG).show();
       }
       return false;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mgoogleMap=googleMap;
        gotolocation(25.7681018,-80.1359467,15);
    }

    private void gotolocation(double lat, double lng, float zoom) {

        LatLng ll=new LatLng(lat,lng);
        CameraUpdate update= CameraUpdateFactory.newLatLngZoom(ll,zoom);
        mgoogleMap.moveCamera(update);

    }
}
