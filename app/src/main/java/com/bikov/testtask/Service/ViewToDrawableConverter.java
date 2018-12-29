package com.bikov.testtask.Service;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.View;

public class ViewToDrawableConverter implements Runnable{

    private Context context;
    private View rootView;
    private Bitmap drawable;

    public ViewToDrawableConverter(Context context, View rootView) {
        this.context = context;
        this.rootView = rootView;
    }

    @Override
    public void run() {
        drawable = createDrawableFromView(context, rootView);
    }

    private Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    public Bitmap getDrawable(){
        return drawable;
    }
}
