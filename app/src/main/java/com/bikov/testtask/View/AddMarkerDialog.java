package com.bikov.testtask.View;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.bikov.testtask.Entity.MapMarker;
import com.bikov.testtask.R;

public class AddMarkerDialog {
    private AlertDialog.Builder addMarkerDialog;
    private Context context;
    private View dialogView;
    private EditText title;
    private EditText subtitle;
    private EditText lat;
    private EditText lng;

    public AddMarkerDialog(Context context) {
        this.context = context;

        addMarkerDialog = new AlertDialog.Builder(context);
        initViewFields();
    }

    private void initViewFields() {
        dialogView = View.inflate(context, R.layout.dialog_add_marker, null);

        title = dialogView.findViewById(R.id.editText_title);
        subtitle = dialogView.findViewById(R.id.editText_subtitle);
        lat = dialogView.findViewById(R.id.editText_lat);
        lng = dialogView.findViewById(R.id.editText_lng);
    }

    public AlertDialog.Builder showDialog(final Callback callback) {
        addMarkerDialog.setView(dialogView);
        addMarkerDialog.setPositiveButton(context.getResources().getText(R.string.add_marker_positive),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Double latD = Double.parseDouble((lat.getText()).toString());
                        Double lngD = Double.parseDouble((lng.getText()).toString());
                        callback.onSuccess(new MapMarker(title.getText().toString(), subtitle.getText().toString(), BitmapFactory.decodeResource(context.getResources(), R.drawable.putin), latD, lngD));
                        dialog.dismiss();
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

    public interface Callback {

        void onSuccess(MapMarker marker);

    }

}
