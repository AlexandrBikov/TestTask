package com.bikov.testtask.View;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bikov.testtask.Service.MapDataManager;
import com.bikov.testtask.Service.MapMarker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.bikov.testtask.R;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mapView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    mapView = (MapView)inflater.inflate(R.layout.fragment_map, null);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(this);
    return mapView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        MapDataManager dm = MapDataManager.getInstance(this.getContext());

        for(MapMarker marker: dm.getMarkerList().getList()) {
            marker.convertToBitmap(getContext());
        }

        for(MapMarker marker: dm.getMarkerList().getList()){
            mMap.addMarker(new MarkerOptions().position(marker.getCoordinates()).icon(BitmapDescriptorFactory.fromBitmap(marker.getMarkerBitmap())));
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.9, 27.57), 10));
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
