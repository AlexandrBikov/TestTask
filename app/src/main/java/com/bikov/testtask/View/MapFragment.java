package com.bikov.testtask.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import com.bikov.testtask.Entity.MapMarker;
import com.bikov.testtask.R;
import com.bikov.testtask.Service.GoogleMapsManager;
import com.bikov.testtask.Service.MapDataManager;
import com.bikov.testtask.Service.MapManager;
import com.bikov.testtask.Service.MapboxManager;


public class MapFragment extends Fragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private MapManager mapManager;
    private GoogleMapsManager googleMapsManager;
    private MapboxManager mapboxManager;
    private PopupMenu menu;
    private ImageButton menuButton;
    private RelativeLayout rootView;
    private View googleMapsView;
    private View mapboxView;
    private MapDataManager dataManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (RelativeLayout) inflater.inflate(R.layout.fragment_map, null);

        dataManager = MapDataManager.getInstance(getContext());

        initGoogleMaps();
        initMapbox();
        initMenuButton();
        initPopupMenu();
        initMap();

        return rootView;
    }

    private void initGoogleMaps() {
        googleMapsManager = GoogleMapsManager.getInstance();
        googleMapsView = googleMapsManager.getMapView();
    }

    private void initMapbox() {
        mapboxManager = MapboxManager.getInstance();
        mapboxView = mapboxManager.getMapView();
    }

    private void initMap() {
        mapManager = googleMapsManager;

        ConstraintLayout mapContainer = rootView.findViewById(R.id.map_container);
        mapContainer.addView(googleMapsView);
        mapContainer.addView(mapboxView);

        mapboxView.setVisibility(View.INVISIBLE);
    }

    private void initMenuButton() {
        menuButton = rootView.findViewById(R.id.menu_button);
        menuButton.setOnClickListener(this);
    }

    private void initPopupMenu() {
        menu = new PopupMenu(getContext(), menuButton);
        menu.getMenuInflater().inflate(R.menu.map_menu, menu.getMenu());
        menu.setOnMenuItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        menu.show();
    }

    private void startAddMarkerDialog() {
        AddMarkerDialog addMarkerDialog = new AddMarkerDialog(getContext());
        addMarkerDialog.showDialog(new AddMarkerDialog.Callback() {
            @Override
            public void onSuccess(MapMarker marker) {
                dataManager.addMarker(marker);
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.menu_google_maps:
                changeToGoogleMaps();
                break;
            case R.id.menu_mapbox:
                changeToMapbox();
                break;
            case R.id.add_marker_button:
                startAddMarkerDialog();
                break;
        }
        return true;
    }

    private void changeToMapbox() {
        mapboxView.setVisibility(View.VISIBLE);
        googleMapsView.setVisibility(View.INVISIBLE);
    }

    private void changeToGoogleMaps() {
        googleMapsView.setVisibility(View.VISIBLE);
        mapboxView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapManager.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapManager.onStop();
    }

    @Override
    public void onResume() {
        mapManager.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapManager.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapManager.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapManager.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapManager.onSaveInstanceState(outState);
    }
}
