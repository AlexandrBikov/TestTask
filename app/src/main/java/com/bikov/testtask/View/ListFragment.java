package com.bikov.testtask.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import java.util.List;

public class ListFragment extends Fragment {

    private RecyclerView list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, null);

        list = rootView.findViewById(R.id.rv);

        LinearLayoutManager listManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(listManager);
        list.setHasFixedSize(true);

        MapDataManager dataManager = MapDataManager.getInstance(getContext());
        List<MapMarker> markers = dataManager.getMarkerList().getList();

        RVAdapter adapter = new RVAdapter(markers);
        list.setAdapter(adapter);

        return rootView;
    }
}
