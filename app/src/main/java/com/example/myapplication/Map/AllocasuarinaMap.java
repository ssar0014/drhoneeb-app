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

public class AllocasuarinaMap extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mapAPI;
    SupportMapFragment mapFragment;
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latLngs = new ArrayList<>();


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
                        .position(new LatLng(-43.45999647, 147.1942206))
                        .title("Italian Allocasuarina verticillata 1")
                        .snippet("This is Allocasuarina verticillata")
                        .alpha(0.8f)
                        .icon(bitmapDescriptorFromVector(this, R.drawable.flower_marker_20dp))

        );
        mapAPI.addMarker(
                new MarkerOptions()
                        .position(new LatLng(-35.33, 149.13))
                        .title("Italian Allocasuarina verticillata 2")
                        .snippet("This is Allocasuarina verticillata")
                        .alpha(0.8f)
                        .icon(bitmapDescriptorFromVector(this, R.drawable.flower_marker_20dp))

        );
        mapAPI.addMarker(
                new MarkerOptions()
                        .position(new LatLng(-34.87, 138.7))
                        .title("Italian Allocasuarina verticillata 3")
                        .snippet("This is Allocasuarina verticillata")
                        .alpha(0.8f)
                        .icon(bitmapDescriptorFromVector(this, R.drawable.flower_marker_20dp))

        );
        mapAPI.addMarker(
                new MarkerOptions()
                        .position(new LatLng(-27.4667, 153.0167))
                        .title("Italian Allocasuarina verticillata 4")
                        .snippet("This is Allocasuarina verticillata")
                        .alpha(0.8f)
                        .icon(bitmapDescriptorFromVector(this, R.drawable.flower_marker_20dp))

        );
        mapAPI.addMarker(
                new MarkerOptions()
                        .position(new LatLng(-31.546667, 138.603611))
                        .title("Italian Allocasuarina verticillata 5")
                        .snippet("This is Allocasuarina verticillata")
                        .alpha(0.8f)
                        .icon(bitmapDescriptorFromVector(this, R.drawable.flower_marker_20dp))

        );
        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-30.166667, 136.833333)));
    }
}
