package com.bikov.testtask.Service;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.bikov.testtask.Entity.MapMarker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class GoogleMapsManager implements OnMapReadyCallback, MapManager {
    private MapView mapView;
    private Context context;
    private ArrayList<MapMarker> markerList;
    private GoogleMap map;
    private static GoogleMapsManager instance;

    public static synchronized GoogleMapsManager getInstance() {
        return instance;
    }

    public static synchronized GoogleMapsManager getInstance(Context context, ArrayList<MapMarker> markerList) {
        if(instance == null) {
            instance = new GoogleMapsManager(context, markerList);
        }
        return instance;
    }

    private GoogleMapsManager(Context context, ArrayList<MapMarker> markerList) {
        this.context = context;
        this.markerList = markerList;

        initMapView(null);
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        map = mMap;

        for (MapMarker marker : markerList) {
            addMarker(marker);
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.9, 27.57), 10));
    }

    @Override
    public void delete(MapMarker marker){
        markerList.remove(marker);
        map.clear();
        for(MapMarker mapMarker : markerList){
            addMarker(mapMarker);
        }
    }

    @Override
    public void addMarker(MapMarker marker){
        LatLng coordinates = new LatLng(marker.getLat(), marker.getLng());
        if(!marker.hasBitmap()){
            marker.convertToBitmap(context);
        }
        map.addMarker(new MarkerOptions().position(coordinates).icon(BitmapDescriptorFactory.fromBitmap(marker.getMarkerBitmap())));
    }

    private void initMapView(Bundle savedInstanceState){
        mapView = new MapView(context);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    public View getMapView() {
        return mapView;
    }

    @Override
    public void onResume() {
        mapView.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        mapView.onStart();
    }

    @Override
    public void onStop() {
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
    }
}
