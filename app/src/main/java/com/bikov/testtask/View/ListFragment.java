package com.bikov.testtask.View;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bikov.testtask.Entity.MapMarker;
import com.bikov.testtask.Entity.MarkerList;
import com.bikov.testtask.R;
import com.bikov.testtask.Service.MapDataManager;
import com.bikov.testtask.Service.RVAdapter;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class ListFragment extends Fragment implements View.OnClickListener {

    private RecyclerView list;
    private MarkerList markers;
    private RVAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, null);

        list = rootView.findViewById(R.id.rv);

        LinearLayoutManager listManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(listManager);
        list.setHasFixedSize(true);

        MapDataManager dataManager = MapDataManager.getInstance(getContext());
        markers = dataManager.getMarkerList();

        adapter = new RVAdapter(markers.getList());
        list.setAdapter(adapter);

        FloatingActionButton addMarkerButton = rootView.findViewById(R.id.add_marker_button);
        addMarkerButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        startAddMarkerDialog();
        adapter.notifyDataSetChanged();
    }

    private void startAddMarkerDialog(){
        View dialogView = View.inflate(getContext(), R.layout.dialog_add_marker, null);
        final EditText title = dialogView.findViewById(R.id.editText_title);
        final EditText subtitle = dialogView.findViewById(R.id.editText_subtitle);
        final EditText lat = dialogView.findViewById(R.id.editText_lat);
        final EditText lng = dialogView.findViewById(R.id.editText_lng);

        AlertDialog.Builder addMarkerDialog = new AlertDialog.Builder(getContext());
        addMarkerDialog.setView(dialogView);
        addMarkerDialog.setPositiveButton(getResources().getText(R.string.add_marker_positive),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Double latD = Double.parseDouble((lat.getText()).toString());
                        Double lngD = Double.parseDouble((lng.getText()).toString());
                        markers.add(title.getText().toString(), subtitle.getText().toString(), getResources().getDrawable(R.drawable.putin), new LatLng(latD, lngD));
                        dialog.dismiss();
                    }
                })

                .setNegativeButton(getResources().getText(R.string.add_marker_negative),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        addMarkerDialog.create();
        addMarkerDialog.show();
    }
}
