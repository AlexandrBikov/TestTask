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
    static class MarkerViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title;
        TextView subtitle;
        ImageView icon;

        MarkerViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.list_item);
            title = itemView.findViewById(R.id.title_list);
            subtitle = itemView.findViewById(R.id.subtitle_list);
            icon = itemView.findViewById(R.id.icon_list);
        }

        void bind(final MapMarker marker, final OnItemClickListener listener){
            title.setText(marker.getTitle());
            subtitle.setText(marker.getSubtitle());
            icon.setImageBitmap(marker.getIcon());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(marker);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(MapMarker marker);
    }

    private List<MapMarker> markers;
    private OnItemClickListener listener;

    public RVAdapter(List<MapMarker> markers, OnItemClickListener listener) {
        this.listener = listener;
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
        markerViewHolder.bind(markers.get(i), listener);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
