package com.example.lab9;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends Fragment implements OnMapReadyCallback {

    public static GoogleMap gMap;
    public MarkerDao markerDao;
    MainActivity mainActivity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.gMap = googleMap;
        List<Marker> markers = markerDao.getAll();
        for (Marker i:
                markers) {
            addMarkerToMap(i.latitude, i.longitude);
        }
    }

    public void addMarkerFromUserInput(String latitudeStr, String longitudeStr) {
        if (gMap != null && !latitudeStr.isEmpty() && !longitudeStr.isEmpty()) {
            double latitude = Double.parseDouble(latitudeStr);
            double longitude = Double.parseDouble(longitudeStr);

            LatLng userLocation = new LatLng(latitude, longitude);
            gMap.addMarker(new MarkerOptions().position(userLocation).title("User Marker"));
            gMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
        }
    }
    public MapsActivity (MarkerDao markerDao){
        this.markerDao = markerDao;
    }
    void addMarkerToMap(double lat, double longi){
        LatLng pos = new LatLng(lat, longi);
        gMap.addMarker(new MarkerOptions().position(pos).title(String.valueOf(1)));
    }
    void clearMap(){
        gMap.clear();
    }
}

