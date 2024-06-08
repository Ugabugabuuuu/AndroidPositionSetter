package com.example.lab9;

import com.google.android.gms.maps.model.LatLng;

public class MarkerPoint {
    private LatLng coordinates;
    public MarkerPoint(LatLng coordinates) {
        this.coordinates = coordinates;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }

}
