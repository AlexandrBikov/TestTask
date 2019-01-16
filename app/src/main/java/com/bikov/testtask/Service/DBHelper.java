package com.bikov.testtask.Service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context,"myDB", null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table markers (id integer primary key autoincrement, title text, subtitle text, lat real, lng real, icon blob, hash integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table markers");
        db.execSQL("create table markers (id integer primary key autoincrement, title text, subtitle text, lat real, lng real, icon blob, hash integer)");
    }
}
