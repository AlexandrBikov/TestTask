package com.bikov.testtask.Service;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

    public GoogleMapsManager(Context context, Bundle savedInstanceState) {
        this.context = context;

        initMapView(savedInstanceState);
        initMarkerList();
    }

    @Override
    public void onMapReady(GoogleMap mMap) {

        for (MapMarker marker : markerList) {
            LatLng coordinates = new LatLng(marker.getLat(), marker.getLng());
            mMap.addMarker(new MarkerOptions().position(coordinates).icon(BitmapDescriptorFactory.fromBitmap(marker.getMarkerBitmap())));
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.9, 27.57), 10));
    }

    @Override
    public void addMarker(String title, String subtitle, Drawable icon, double lat, double lng){

    }

    private void initMapView(Bundle savedInstanceState){
        mapView = new MapView(context);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    private void initMarkerList(){
        MapDataManager dm = MapDataManager.getInstance(context);
        markerList = dm.getMarkerList().getList();
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
