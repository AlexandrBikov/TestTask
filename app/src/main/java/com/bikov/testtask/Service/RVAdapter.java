package com.bikov.testtask.Service;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bikov.testtask.Entity.MapMarker;
import com.bikov.testtask.R;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MarkerViewHolder> {
    public static class MarkerViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView title;
        TextView subtitle;
        ImageView icon;
        public MarkerViewHolder(View itemView){
            super(itemView);
            cv = itemView.findViewById(R.id.list_item);
            title = itemView.findViewById(R.id.title_list);
            subtitle = itemView.findViewById(R.id.subtitle_list);
            icon = itemView.findViewById(R.id.icon_list);
        }
    }

    List<MapMarker> markers;
    public RVAdapter(List<MapMarker> markers){
        this.markers = markers;
    }

    @Override
    public int getItemCount() {
        return markers.size();
    }

    @NonNull
    @Override
    public MarkerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.marker_list_item, viewGroup, false);
        return new MarkerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MarkerViewHolder markerViewHolder, int i) {
        markerViewHolder.title.setText(markers.get(i).getTitle());
        markerViewHolder.subtitle.setText(markers.get(i).getSubtitle());
        markerViewHolder.icon.setImageDrawable(markers.get(i).getIcon());
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
