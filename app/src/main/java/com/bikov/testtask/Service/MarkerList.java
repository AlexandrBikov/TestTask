package com.bikov.testtask.Service;

import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MarkerList {
    private ArrayList<MapMarker> markerList;

    public MarkerList(){
        markerList = new ArrayList<>();
    }

    public MarkerList(ArrayList<MapMarker> markerList) {
        this.markerList = markerList;
    }

    public void add(MapMarker marker){
        markerList.add(marker);
    }

    public void add(String title, String subtitle, Drawable icon, LatLng coordinates){
        markerList.add(new MapMarker(title, subtitle, icon, coordinates));
    }

    public ArrayList<MapMarker> getList() {
        return markerList;
    }
}
