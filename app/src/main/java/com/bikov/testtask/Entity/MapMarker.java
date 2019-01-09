package com.bikov.testtask.Entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bikov.testtask.R;
import com.bikov.testtask.Service.ViewToBitmapConverter;
import com.google.android.gms.maps.model.LatLng;

public class MapMarker{

    private String title;
    private String subtitle;
    private Drawable icon;
    private LatLng coordinates;
    private TextView markerTitleView;
    private TextView markerSubtitleView;
    private ImageView markerIconView;
    private ViewToBitmapConverter converter;
    private Thread converterThread;

    public MapMarker(String title, String subtitle, Drawable icon, LatLng coordinates) {
        this.title = title;
        this.subtitle = subtitle;
        this.icon = icon;
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

    public void convertToBitmap(Context context){
        View markerLayout = View.inflate(context, R.layout.map_marker, null);

        markerIconView = markerLayout.findViewById(R.id.icon_marker);
        markerTitleView = markerLayout.findViewById(R.id.title_marker);
        markerSubtitleView = markerLayout.findViewById(R.id.subtitle_marker);

        markerTitleView.setText(title);
        markerSubtitleView.setText(subtitle);
        markerIconView.setImageDrawable(icon);

        converter = new ViewToBitmapConverter(context, markerLayout);
        converterThread = new Thread(converter);
        converterThread.start();

    }

    public Bitmap getMarkerBitmap() {
        try {
            converterThread.join();
        } catch (InterruptedException e){}
        return converter.getBitmap();
    }
}
