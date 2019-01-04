package com.bikov.testtask.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;

import com.bikov.testtask.R;
import com.google.android.gms.maps.model.LatLng;

public class MapDataManager {
    private static MapDataManager instance;
    /*private DBHelper dbHelper;
    private SQLiteDatabase db;*/
    private int titleIndex;
    private int subtitleIndex;
    private int iconIDIndex;
    private int latIndex;
    private int lngIndex;
    private Context context;
    private Cursor cursor;

    public static synchronized MapDataManager getInstance(Context context) {
        if(instance == null) {
            instance = new MapDataManager(context);
        }
        return instance;
    }

    private MapDataManager(Context context) {
        this.context = context;
       /* dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();*/

        addMarkerToDB(cv, "5 Элемент", "Независимости 117", R.drawable.putin, 53.9305300, 27.6346841);
        addMarkerToDB(cv, "5 Элемент", "кульман 14", R.drawable.putin, 53.9210960, 27.5816002);
        addMarkerToDB(cv, "5 Элемент", "Дзержинского 104", R.drawable.putin, 53.8610232, 27.4798459);

        dbHelper.close();
    }

    public MarkerList getMarkerList() {
        db = dbHelper.getReadableDatabase();
        cursor = db.query("markers", null, null, null, null, null, null);
        MarkerList markerList = new MarkerList();

        setColumnIndexes();

        cursor.moveToFirst();
        do{
            markerList.add(getMarkerFromDB());
        } while (cursor.moveToNext());

        cursor.close();
        db.close();
        return markerList;
    }

    private void setColumnIndexes(){
        titleIndex = cursor.getColumnIndex("title");
        subtitleIndex = cursor.getColumnIndex("subtitle");
        iconIDIndex = cursor.getColumnIndex("iconID");
        latIndex = cursor.getColumnIndex("lat");
        lngIndex = cursor.getColumnIndex("lng");
    }

    private void addMarkerToDB(ContentValues cv, String title, String subtitle, int iconID, double lat, double lng){

        cv.put("title", title);
        cv.put("subtitle", subtitle);
        cv.put("iconID", iconID);
        cv.put("lat", lat);
        cv.put("lng", lng);
        int hash = cv.hashCode();
        Cursor c = db.query("markers", null, "hash=" + hash, null, null, null, null);
        if(!c.moveToFirst()) {
            cv.put("hash", hash);
            db.insert("markers", null, cv);
        }
        c.close();
        System.out.println(hash);
    }

    private MapMarker getMarkerFromDB(){
        Drawable icon = context.getResources().getDrawable(cursor.getInt(iconIDIndex));
        LatLng latLng = new LatLng(cursor.getDouble(latIndex), cursor.getDouble(lngIndex));
        return new MapMarker(cursor.getString(titleIndex), cursor.getString(subtitleIndex), icon, latLng);
    }
}
