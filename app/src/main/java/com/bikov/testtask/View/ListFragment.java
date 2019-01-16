package com.bikov.testtask.View;

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

    private void initMarkerList(){
        dataManager = MapDataManager.getInstance(getContext());
        markers = dataManager.getMarkerList().getList();
    }

    private void initListView(){
        list = rootView.findViewById(R.id.rv);
        LinearLayoutManager listManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(listManager);
        list.setHasFixedSize(true);
    }

    private void putData(){
        adapter = new RVAdapter(markers);
        list.setAdapter(adapter);
    }

    private void initAddMarkerDialog(){
        FloatingActionButton addMarkerButton = rootView.findViewById(R.id.add_marker_button);
        addMarkerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startAddMarkerDialog();
        adapter.notifyDataSetChanged();
    }

    private void startAddMarkerDialog(){
        AddMarkerDialog addMarkerDialog = new AddMarkerDialog(getContext());
        addMarkerDialog.showDialog(new AddMarkerDialog.Callback() {
            @Override
            public void onSuccess(MapMarker marker) {
                dataManager.addMarker(marker);
            }
        });
    }
}
