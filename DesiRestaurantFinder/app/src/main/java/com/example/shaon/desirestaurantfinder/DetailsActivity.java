package com.example.shaon.desirestaurantfinder;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailsActivity extends AppCompatActivity {

    WebView webView;
    Results result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        webView = (WebView) findViewById(R.id.webView);
        result = getIntent().getParcelableExtra("result");
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(result.restauranturl);

    }

    public void show_location_in_map(View view) {
        Intent intent = new Intent(this, restaurant_location_in_map.class);
        Bundle b = new Bundle();
        b.putDouble("lattitude", result.latitude);
        b.putDouble("longitude", result.longitude);
        b.putString("title", result.name);
        b.putString("city", result.city);
        intent.putExtras(b);
        startActivity(intent);

    }
}
