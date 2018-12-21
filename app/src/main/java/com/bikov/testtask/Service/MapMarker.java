package com.bikov.testtask.Service;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

public class MapMarker{

    private String title;
    private String subtitle;
    private Drawable icon;
    private LatLng coordinates;

    public MapMarker(String title, String subtitle, Drawable icon, LatLng coordinates) {
        this.title = title;
        this.subtitle = subtitle;
        this.icon = icon;
        this.coordinates = coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public Drawable getIcon() {
        return icon;
    }

}
