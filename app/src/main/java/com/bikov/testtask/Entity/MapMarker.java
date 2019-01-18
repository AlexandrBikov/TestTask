package com.bikov.testtask.Entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bikov.testtask.R;
import com.bikov.testtask.Service.ViewToBitmapConverter;

import java.io.ByteArrayOutputStream;

public class MapMarker {

    private String title;
    private String subtitle;
    private Bitmap icon;
    private ViewToBitmapConverter converter;
    private double lat;
    private double lng;
    private Thread converterThread;
    private Bitmap bitmap;
    private int hash;
    private byte[] blobIcon;

    public MapMarker(String title, String subtitle, Bitmap icon, double lat, double lng) {
        this.title = title;
        this.subtitle = subtitle;
        this.icon = icon;
        this.lat = lat;
        this.lng = lng;
        hash = hashCode();
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

    public Bitmap getIcon() {
        return icon;
    }

    public boolean hasBitmap() {
        return bitmap != null;
    }

    public void convertToBitmap(Context context) {
        View markerLayout = View.inflate(context, R.layout.map_marker, null);

        ImageView markerIconView = markerLayout.findViewById(R.id.icon_marker);
        TextView markerTitleView = markerLayout.findViewById(R.id.title_marker);
        TextView markerSubtitleView = markerLayout.findViewById(R.id.subtitle_marker);

        markerTitleView.setText(title);
        markerSubtitleView.setText(subtitle);
        markerIconView.setImageBitmap(icon);

        markerLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        markerLayout.setPadding(calculatePadding(markerLayout.getMeasuredWidth(), context), 0, 0, 0);

        converter = new ViewToBitmapConverter(context, markerLayout);
        converterThread = new Thread(converter);
        converterThread.start();
    }

    private int calculatePadding(int width, Context context){
        int dpi = context.getResources().getDisplayMetrics().densityDpi;
        return (int)(width - 20*(dpi/160f));
    }

    public int getHash() {
        return hash;
    }

    private byte[] convertBitmapToBlob(Bitmap image) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();
    }

    public byte[] getBlobIcon() {
        if (blobIcon == null) {
            blobIcon = convertBitmapToBlob(icon);
        }
        return blobIcon;
    }

    @Override
    public int hashCode() {
        int result;
        result = title.hashCode();
        result = 31 * result + subtitle.hashCode();
        result = 31 * result + hashBitmap(icon);
        result = 31 * result + (int) (lat * 100000000);
        result = 31 * result + (int) (lng * 100000000);
        return result;
    }

    private int hashBitmap(Bitmap bmp) {
        int hash = 31;
        for (int x = 0; x < bmp.getWidth(); x += 5) {
            for (int y = 0; y < bmp.getHeight(); y += 5) {
                hash *= (bmp.getPixel(x, y) + 31);
            }
        }
        return hash;
    }

    public Bitmap getMarkerBitmap() {
        if (bitmap != null) {
            return bitmap;
        }
        try {
            converterThread.join();
        } catch (InterruptedException e) {
        }
        bitmap = converter.getBitmap();
        return bitmap;
    }
}
