package com.bikov.testtask.Entity;

import android.graphics.Bitmap;

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

    public void add(String title, String subtitle, Bitmap icon, double lat, double lng){
        markerList.add(new MapMarker(title, subtitle, icon, lat, lng));
    }

    public void delete(MapMarker marker){
        markerList.remove(marker);
    }

    public ArrayList<MapMarker> getList() {
        return markerList;
    }
}
