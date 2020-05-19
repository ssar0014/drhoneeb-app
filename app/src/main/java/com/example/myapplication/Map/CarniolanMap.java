package com.example.myapplication.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.myapplication.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class CarniolanMap extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mapAPI;
    SupportMapFragment mapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_italian);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAPI);
        mapFragment.getMapAsync(this);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapAPI = googleMap;
        mapAPI.addMarker(
                new MarkerOptions()
                        .position(new LatLng(-28.9216, 151.9285))
                        .title("Carniolan Bees 1")
                        .snippet("This is Carniolan bees")
                        .alpha(0.8f)
                        .icon(bitmapDescriptorFromVector(this, R.drawable.ic_bee_marker_30dp))

        );
        mapAPI.addMarker(
                new MarkerOptions()
                        .position(new LatLng(-35.648, 150.1387))
                        .title("Carniolan Bees 2")
                        .snippet("This is Carniolan bees")
                        .alpha(0.8f)
                        .icon(bitmapDescriptorFromVector(this, R.drawable.ic_bee_marker_30dp))

        );
        mapAPI.addMarker(
                new MarkerOptions()
                        .position(new LatLng(-33.9374, 118.0076))
                        .title("Carniolan Bees 3")
                        .snippet("This is Carniolan bees")
                        .alpha(0.8f)
                        .icon(bitmapDescriptorFromVector(this, R.drawable.ic_bee_marker_30dp))

        );
        mapAPI.addMarker(
                new MarkerOptions()
                        .position(new LatLng(-32.8225, 139.1791))
                        .title("Carniolan Bees 4")
                        .snippet("This is Carniolan bees")
                        .alpha(0.8f)
                        .icon(bitmapDescriptorFromVector(this, R.drawable.ic_bee_marker_30dp))

        );
        mapAPI.addMarker(
                new MarkerOptions()
                        .position(new LatLng(-12.33, 133.05))
                        .title("Carniolan Bees 5")
                        .snippet("This is Carniolan bees")
                        .alpha(0.8f)
                        .icon(bitmapDescriptorFromVector(this, R.drawable.ic_bee_marker_30dp))

        );
        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-30.166667, 136.833333)));
    }
}
