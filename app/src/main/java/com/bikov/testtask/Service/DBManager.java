package com.bikov.testtask.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bikov.testtask.Entity.MapMarker;

import java.util.ArrayList;

public class DBManager {
    private SQLiteDatabase db;
    private ArrayList<MapMarker> markers;
    private Thread thread;
    private Cursor cursor;

    public DBManager(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        markers = new ArrayList<>();
    }

    public void addMarkerToDB(MapMarker marker) {
        markers.add(marker);
    }

    public void commit() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor c = db.query("markers", new String[]{"hash"}, null, null, null, null, null);
                ArrayList<Integer> hashes = getHashes(c);
                c.close();
                for (MapMarker marker : markers) {
                    if (!hashes.contains(marker.getHash())) {
                        db.insert("markers", null, makeCV(marker));
                    }
                }
                cursor = db.query("markers", null, null, null, null, null, null);
            }
        });
        thread.start();
    }

    public void delete(int hash) {
        db.delete("markers", "hash = '" + hash + "'", null);
    }

    private ArrayList<Integer> getHashes(Cursor c) {
        int hashIndex = c.getColumnIndex("hash");
        ArrayList<Integer> hashes = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                hashes.add(c.getInt(hashIndex));
            } while (c.moveToNext());
        }
        return hashes;
    }

    private ContentValues makeCV(MapMarker marker) {
        ContentValues cv = new ContentValues();
        cv.put("title", marker.getTitle());
        cv.put("subtitle", marker.getSubtitle());
        cv.put("icon", marker.getBlobIcon());
        cv.put("lat", marker.getLat());
        cv.put("lng", marker.getLng());
        cv.put("hash", marker.getHash());
        return cv;
    }

    public Cursor getCursor() {
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
        }
        return cursor;
    }
}
