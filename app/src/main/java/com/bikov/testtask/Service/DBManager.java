package com.bikov.testtask.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBManager {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private ArrayList<ContentValues> values;
    private Thread thread;
    private Cursor cursor;

    public DBManager(Context context){
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        values = new ArrayList<>();
    }

    public void addMarkerToDB(String title, String subtitle, int iconID, double lat, double lng){
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("subtitle", subtitle);
        cv.put("iconID", iconID);
        cv.put("lat", lat);
        cv.put("lng", lng);
        values.add(cv);
    }

    public void commit(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(ContentValues cv : values) {
                    int hash = cv.hashCode();
                    Cursor c = db.query("markers", null, "hash=" + hash, null, null, null, null);
                    if (!c.moveToFirst()) {
                        cv.put("hash", hash);
                        db.insert("markers", null, cv);
                    }
                    c.close();
                }
                cursor = db.query("markers", null, null, null, null, null, null);
            }
        });
        thread.start();
    }

    public Cursor getCursor(){
        try {
            thread.join();
        } catch (InterruptedException e){}
        return cursor;
    }
}
