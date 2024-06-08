package com.example.lab9;
import static androidx.core.location.LocationManagerCompat.getCurrentLocation;
import static com.example.lab9.MapsActivity.gMap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.room.Update;
import android.Manifest;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab9.ItemViewModel;
import com.example.lab9.MapsActivity;
import com.example.lab9.MarkerPoint;
import com.example.lab9.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button addMarker;
    ItemViewModel viewModel;
    //MapsActivity mapsActivity = new MapsActivity();

    List<MarkerPoint> markerPoints = new ArrayList<>();

    EditText inputLatitude;
    EditText inputLongitude;
    Button removeMarkers;
    private FusedLocationProviderClient fusedLocationProviderClient;
    Button thisPlace;
    public static AppDatabase db;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "coordinates-database").allowMainThreadQueries().build();

        MarkerDao markerDao = db.markerDao();
        loadFragment(new MapsActivity(markerDao));

        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        thisPlace = findViewById(R.id.getCurrentCordinates);
        inputLatitude = findViewById(R.id.inputLatitude);
        inputLongitude = findViewById(R.id.inputLongitude);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        addMarker = findViewById(R.id.addMarkerButton);
        addMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get latitude and longitude from EditText views
                String latitudeStr = inputLatitude.getText().toString();
                String longitudeStr = inputLongitude.getText().toString();
                double latitude = Double.parseDouble(latitudeStr);
                double longitude = Double.parseDouble(longitudeStr);

                if (!latitudeStr.isEmpty() && !longitudeStr.isEmpty()) {
                    addMarkerToMap(latitude, longitude);

                    LatLng coordinates = new LatLng(latitude, longitude);

                    MarkerPoint markerPoint = new MarkerPoint(coordinates);
                    markerPoints.add(markerPoint);
                    inputLatitude.getText().clear();
                    inputLongitude.getText().clear();
                    Marker marker = new Marker(latitude, longitude);
                    markerDao.insertCord(marker);


                }
            }
        });
        removeMarkers = findViewById(R.id.deleteAllCordinates);
        removeMarkers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markerDao.deleteAll();
                clearMap();
            }
        });
        thisPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            LOCATION_PERMISSION_REQUEST_CODE);
                }
            }
        });

    }

    void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
    private void updateMapWithLastKnownLocation() {
         fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            LatLng userLocation = new LatLng(latitude, longitude);

                            addMarkerToMap(latitude, longitude);
                        }
                    }
                });
    }
    void addMarkerToMap(double lat, double longi){
        LatLng pos = new LatLng(lat, longi);
        gMap.addMarker(new MarkerOptions().position(pos).title(String.valueOf(1)));
    }
    void clearMap(){
        gMap.clear();
    }
    private void getCurrentLocation() {
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            inputLatitude.setText(String.valueOf(latitude));
                            inputLongitude.setText(String.valueOf(longitude));
                        } else {
                            Log.d("labas", "Location is null");
                        }
                    }
                });
    }
}
