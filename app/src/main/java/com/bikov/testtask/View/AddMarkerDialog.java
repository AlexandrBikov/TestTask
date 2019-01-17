package com.bikov.testtask.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bikov.testtask.Entity.MapMarker;
import com.bikov.testtask.R;

import java.io.IOException;

public class AddMarkerDialog implements View.OnClickListener {
    private AlertDialog.Builder addMarkerDialog;
    private Context context;
    private View dialogView;
    private EditText titleView;
    private EditText subtitleView;
    private EditText latView;
    private EditText lngView;
    private ImageView iconView;
    private Bitmap icon;
    private Fragment fragment;
    private String title;
    private String subtitle;
    private double lat;
    private double lng;

    private static final int READ_REQUEST_CODE = 42;

    public AddMarkerDialog(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;

        icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.no_photo_icon);

        addMarkerDialog = new AlertDialog.Builder(context);
        initViewFields();
        iconView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_icon:
                changeProfileImage();
                break;
        }
    }

    private void changeProfileImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        fragment.startActivityForResult(intent, READ_REQUEST_CODE);
    }

    public void setIconUri(Uri iconUri) {
        if (iconUri != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), iconUri);
                icon = compressImage(bitmap);
            } catch (IOException e) {
            }
        }
        iconView.setImageBitmap(icon);
    }

    private Bitmap compressImage(Bitmap image) {
        int width = image.getWidth();
        int height = image.getHeight();

        return Bitmap.createScaledBitmap(image, width / 4, height / 4, false);
    }

    private void initViewFields() {
        dialogView = View.inflate(context, R.layout.dialog_add_marker, null);

        iconView = dialogView.findViewById(R.id.imageView_icon);
        titleView = dialogView.findViewById(R.id.editText_title);
        subtitleView = dialogView.findViewById(R.id.editText_subtitle);
        latView = dialogView.findViewById(R.id.editText_lat);
        lngView = dialogView.findViewById(R.id.editText_lng);
    }

    public AlertDialog.Builder showDialog(final Callback callback) {
        addMarkerDialog.setView(dialogView);
        addMarkerDialog.setPositiveButton(context.getResources().getText(R.string.add_marker_positive),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getData();
                        if (isMarkerCorrect()) {
                            callback.onSuccess(new MapMarker(title, subtitle, icon, lat, lng));
                            dialog.dismiss();
                        }
                    }
                })
                .setNegativeButton(context.getResources().getText(R.string.add_marker_negative),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        addMarkerDialog.create();
        addMarkerDialog.show();

        return addMarkerDialog;
    }

    private void getData() {
        lat = Double.parseDouble((latView.getText()).toString());
        lng = Double.parseDouble((lngView.getText()).toString());
        title = titleView.getText().toString();
        subtitle = subtitleView.getText().toString();
    }

    private boolean isMarkerCorrect() {
        titleView.setError(null);
        lngView.setError(null);
        latView.setError(null);
        subtitleView.setError(null);

        View focusView = null;
        boolean error = false;

        if (title.isEmpty()) {
            titleView.setError(context.getString(R.string.error_field_required));
            focusView = titleView;
            error = true;
        }

        if (subtitle.isEmpty()) {
            subtitleView.setError(context.getString(R.string.error_field_required));
            focusView = subtitleView;
            error = true;
        }

        if (error) {
            focusView.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    public interface Callback {

        void onSuccess(MapMarker marker);

    }

}
