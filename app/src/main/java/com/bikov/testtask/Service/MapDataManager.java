package com.bikov.testtask.Service;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bikov.testtask.Entity.MapMarker;
import com.bikov.testtask.Entity.MarkerList;
import com.bikov.testtask.R;

public class MapDataManager {
    private static MapDataManager instance;
    private int titleIndex;
    private int subtitleIndex;
    private int iconIndex;
    private int latIndex;
    private int lngIndex;
    private Context context;
    private Cursor cursor;
    private DBManager dbManager;
    private MarkerList markerList;
    private MapboxManager mapboxManager;
    private GoogleMapsManager googleMapsManager;

    public static synchronized MapDataManager getInstance(Context context) {
        if (instance == null) {
            instance = new MapDataManager(context);
        }
        return instance;
    }

    private MapDataManager(Context context) {
        this.context = context;
        dbManager = new DBManager(context);

        addDataToDB();

        markerList = getMarkerList();

        initMapsManagers();
    }

    private void addDataToDB() {
        Bitmap putin = BitmapFactory.decodeResource(context.getResources(), R.drawable.putin);

        dbManager.addMarkerToDB(new MapMarker("5 Элемент", "Независимости 117",putin, 53.9305300, 27.6346841));
        dbManager.addMarkerToDB(new MapMarker("5 Элемент", "кульман 14", putin, 53.9210960, 27.5816002));
        dbManager.addMarkerToDB(new MapMarker("5 Элемент", "Дзержинского 104", putin, 53.8610232, 27.4798459));
        dbManager.commit();
    }

    private void initMapsManagers(){
        mapboxManager = MapboxManager.getInstance(context, markerList.getList());
        googleMapsManager = GoogleMapsManager.getInstance(context, markerList.getList());
    }

    public void addMarker(MapMarker marker) {
        dbManager.addMarkerToDB(marker);
        dbManager.commit();
        markerList.add(marker);
        mapboxManager.addMarker(marker);
        googleMapsManager.addMarker(marker);
    }

    public MarkerList getMarkerList() {
        if (markerList == null) {
            cursor = dbManager.getCursor();
            markerList = new MarkerList();

            setColumnIndexes();
            cursor.moveToFirst();
            do {
                markerList.add(getMarkerFromDB());
            } while (cursor.moveToNext());
            convertMarkersToBitmap();
        }
        return markerList;
    }

    private void convertMarkersToBitmap() {
        for (MapMarker marker : markerList.getList()) {
            marker.convertToBitmap(context);
        }
    }

    private void setColumnIndexes() {
        titleIndex = cursor.getColumnIndex("title");
        subtitleIndex = cursor.getColumnIndex("subtitle");
        iconIndex = cursor.getColumnIndex("icon");
        latIndex = cursor.getColumnIndex("lat");
        lngIndex = cursor.getColumnIndex("lng");
    }

    private MapMarker getMarkerFromDB() {
        byte[] byteArray = cursor.getBlob(iconIndex);
        Bitmap icon = BitmapFactory.decodeByteArray(byteArray, 0 ,byteArray.length);
        return new MapMarker(cursor.getString(titleIndex), cursor.getString(subtitleIndex), icon, cursor.getDouble(latIndex), cursor.getDouble(lngIndex));
    }
}
