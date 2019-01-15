package com.bikov.testtask.Service;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

public interface MapManager {
    View getMapView();
    void onResume();
    void onStart();
    void onStop();
    void onDestroy();
    void onPause();
    void onLowMemory();
    void onSaveInstanceState(Bundle outState);
    void addMarker(String title, String subtitle, Drawable icon, double lat, double lng);
}
