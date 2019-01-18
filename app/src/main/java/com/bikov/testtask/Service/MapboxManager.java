package com.bikov.testtask.Service;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.bikov.testtask.Entity.MapMarker;
import com.bikov.testtask.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.ArrayList;

public class MapboxManager implements OnMapReadyCallback, MapManager {
    private MapView mapView;
    private Context context;
    private ArrayList<MapMarker> markerList;
    private MapboxMap map;
    private IconFactory iconFactory;
    private static MapboxManager instance;

    public static synchronized MapboxManager getInstance() {
        return instance;
    }

    public static synchronized MapboxManager getInstance(Context context, ArrayList<MapMarker> markerList) {
        if(instance == null) {
            instance = new MapboxManager(context, markerList);
        }
        return instance;
    }

    private MapboxManager(Context context, ArrayList<MapMarker> markerList) {
        this.markerList = markerList;
        this.context = context;

        Mapbox.getInstance(context.getApplicationContext(), context.getString(R.string.mapbox_access_token));
        iconFactory = IconFactory.getInstance(context);

        initMapView(null);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        map = mapboxMap;

        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

            }
        });
        for (MapMarker marker : markerList) {
            addMarker(marker);
        }
    }

    @Override
    public void addMarker(MapMarker marker){
        LatLng coordinates = new LatLng(marker.getLat(), marker.getLng());
        if(!marker.hasBitmap()){
            marker.convertToBitmap(context);
        }
        map.addMarker(new MarkerOptions().icon(iconFactory.fromBitmap(marker.getMarkerBitmap())).position(coordinates));
    }

    @Override
    public void delete(MapMarker marker){
        markerList.remove(marker);
        map.clear();
        for(MapMarker mapMarker : markerList){
            addMarker(mapMarker);
        }
    }

    private void initMapView(Bundle savedInstanceState) {
        mapView = new MapView(context);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
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
