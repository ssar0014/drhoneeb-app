package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mapAPI;
    SupportMapFragment mapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAPI);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapAPI = googleMap;
        LatLng ItalianBee1 = new LatLng(-35.96, 144.37);
        mapAPI.addMarker(new MarkerOptions().position(ItalianBee1).title("Italian Bees"));
        LatLng ItalianBee2 = new LatLng(-35.35, 148.83);
        mapAPI.addMarker(new MarkerOptions().position(ItalianBee2).title("Italian Bees"));
        LatLng ItalianBee3 = new LatLng(-30.11, 145.96);
        mapAPI.addMarker(new MarkerOptions().position(ItalianBee3).title("Italian Bees"));
        LatLng ItalianBee4 = new LatLng(-35.88, 148.50);
        mapAPI.addMarker(new MarkerOptions().position(ItalianBee4).title("Italian Bees"));
        LatLng ItalianBee5 = new LatLng(-40.99, 145.73);
        mapAPI.addMarker(new MarkerOptions().position(ItalianBee5).title("Italian Bees"));
        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(ItalianBee1));
    }
}
