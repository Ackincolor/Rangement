package com.ackincolor.rangement.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ackincolor.rangement.R;
import com.ackincolor.rangement.controllers.ClickRangementcontroller;
import com.ackincolor.rangement.models.Rangement;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RangementAdapter extends RecyclerView.Adapter<RangementAdapter.RangementViewHolder> {
    private LayoutInflater inflater;
    private List<Rangement> rangements;
    private ClickRangementcontroller clickRangementcontroller;

    public static final int COMMON = 1;
    public static final int SEPARATOR = 2;


    public RangementAdapter(LayoutInflater inflater, List<Rangement> rangements, ClickRangementcontroller clickRangementcontroller) {
        this.inflater = inflater;
        this.rangements = rangements;
        this.clickRangementcontroller = clickRangementcontroller;
    }

    public void setRangements(List<Rangement> rangements){
        this.rangements = rangements;
    }

    @Override
    public int getItemCount() {
        return rangements.size();
    }


    @NonNull
    @Override
    public RangementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRangement = inflater.inflate(R.layout.rangement_custom_view,parent,false);
        itemRangement.setOnClickListener(this.clickRangementcontroller);
        RangementViewHolder rangementViewHolder = new RangementViewHolder(itemRangement);
        rangementViewHolder.view = itemRangement;
        rangementViewHolder.from = (TextView) itemRangement.findViewById(R.id.tvFrom);
        rangementViewHolder.message = (TextView) itemRangement.findViewById(R.id.tvMessage);
        rangementViewHolder.date = (TextView) itemRangement.findViewById(R.id.tvDate);
        return rangementViewHolder;
    }

    @Override
    public void onBindViewHolder(RangementViewHolder holder, int position) {
        Rangement message = rangements.get(position);
        holder.from.setText(message.getNom());
        holder.message.setText(message.getVolumeTexte());
        holder.date.setText("date");
        holder.position = position;
    }

    public static class RangementViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView from, message, date;
        int position;

        public RangementViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return COMMON;
    }
}
