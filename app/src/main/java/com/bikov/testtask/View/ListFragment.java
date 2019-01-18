package com.bikov.testtask.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bikov.testtask.Entity.MapMarker;
import com.bikov.testtask.R;
import com.bikov.testtask.Service.MapDataManager;
import com.bikov.testtask.Service.RVAdapter;

import java.util.ArrayList;

public class ListFragment extends Fragment implements View.OnClickListener {

    private RecyclerView list;
    private ArrayList<MapMarker> markers;
    private RVAdapter adapter;
    private View rootView;
    private MapDataManager dataManager;
    private EditMarkerDialog editMarkerDialog;

    private static final int READ_REQUEST_CODE = 42;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list, null);

        initListView();
        initMarkerList();
        putData();
        initAddMarkerDialog();


        return rootView;
    }

    private void initMarkerList() {
        dataManager = MapDataManager.getInstance(getContext());
        markers = dataManager.getMarkerList().getList();
    }

    private void initListView() {
        list = rootView.findViewById(R.id.rv);
        LinearLayoutManager listManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(listManager);
        list.setHasFixedSize(true);
    }

    private void putData() {
        final Fragment fragment = this;
        adapter = new RVAdapter(markers, new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final MapMarker marker) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setItems(R.array.marker_menu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                editMarkerDialog = new EditMarkerDialog(getContext(), fragment, marker);
                                editMarkerDialog.showDialog(new EditMarkerDialog.Callback() {
                                    @Override
                                    public void onSuccess(MapMarker newMarker) {
                                        dataManager.deleteMarker(marker);
                                        markers = dataManager.addMarker(newMarker);
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                                break;
                            case 1:
                                markers = dataManager.deleteMarker(marker);
                                adapter.notifyDataSetChanged();
                                break;
                        }
                    }
                });
                builder.show();
            }
        });
        list.setAdapter(adapter);
    }

    private void initAddMarkerDialog() {
        FloatingActionButton addMarkerButton = rootView.findViewById(R.id.add_marker_button);
        addMarkerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startAddMarkerDialog();
        adapter.notifyDataSetChanged();
    }

    private void startAddMarkerDialog() {
        editMarkerDialog = new EditMarkerDialog(getContext(), this);
        editMarkerDialog.showDialog(new EditMarkerDialog.Callback() {
            @Override
            public void onSuccess(MapMarker marker) {
                dataManager.addMarker(marker);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                editMarkerDialog.setIconUri(resultData.getData());
            }
        }
    }
}
