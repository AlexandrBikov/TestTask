package com.bikov.testtask.Service;

import android.os.Bundle;
import android.view.View;

import com.bikov.testtask.Entity.MapMarker;

public interface MapManager {
    View getMapView();
    void onResume();
    void onStart();
    void onStop();
    void onDestroy();
    void onPause();
    void onLowMemory();
    void onSaveInstanceState(Bundle outState);
    void addMarker(MapMarker marker);
    void delete(MapMarker marker);
}
