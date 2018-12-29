package com.bikov.testtask.Service;

import android.content.Context;
import com.bikov.testtask.R;
import com.google.android.gms.maps.model.LatLng;

public class MapDataManager {
    private static MapDataManager instance;
    private MarkerList markerList;

    public static synchronized MapDataManager getInstance(Context context) {
        if(instance == null) {
            instance = new MapDataManager(context);
        }
        return instance;
    }

    private MapDataManager(Context context) {
        markerList = new MarkerList();
        markerList.add("5 Элемент", "Независимости 117", context.getResources().getDrawable(R.drawable.putin), new LatLng(53.9305300, 27.6346841));
        markerList.add("5 Элемент", "кульман 14", context.getResources().getDrawable(R.drawable.putin), new LatLng(53.9210960, 27.5816002));
        markerList.add("5 Элемент", "Дзержинского 104", context.getResources().getDrawable(R.drawable.putin), new LatLng(53.8610232, 27.4798459));
    }

    public MarkerList getMarkerList() {
        return markerList;
    }
}
