package com.bikov.testtask.Entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bikov.testtask.R;
import com.bikov.testtask.Service.ViewToBitmapConverter;

public class MapMarker{

    private String title;
    private String subtitle;
    private Drawable icon;
    private ViewToBitmapConverter converter;
    private double lat;
    private double lng;
    private Thread converterThread;
    private Bitmap bitmap;

    public MapMarker(String title, String subtitle, Drawable icon, double lat, double lng) {
        this.title = title;
        this.subtitle = subtitle;
        this.icon = icon;
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
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
        if(bitmap == null) {
            View markerLayout = View.inflate(context, R.layout.map_marker, null);

            ImageView markerIconView = markerLayout.findViewById(R.id.icon_marker);
            TextView markerTitleView = markerLayout.findViewById(R.id.title_marker);
            TextView markerSubtitleView = markerLayout.findViewById(R.id.subtitle_marker);

            markerTitleView.setText(title);
            markerSubtitleView.setText(subtitle);
            markerIconView.setImageDrawable(icon);

            converter = new ViewToBitmapConverter(context, markerLayout);
            converterThread = new Thread(converter);
            converterThread.start();
        }
    }

    public Bitmap getMarkerBitmap() {
        if(bitmap != null){
            return bitmap;
        }
        try {
            converterThread.join();
        } catch (InterruptedException e){}
        bitmap = converter.getBitmap();
        return bitmap;
    }
}
