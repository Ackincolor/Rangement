package com.ackincolor.rangement.ui.objet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ackincolor.rangement.R;
import com.ackincolor.rangement.controllers.ClickObjetcontroller;
import com.ackincolor.rangement.controllers.ClickRangementcontroller;
import com.ackincolor.rangement.models.Objet;
import com.ackincolor.rangement.models.Rangement;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ObjetAdapter extends RecyclerView.Adapter<ObjetAdapter.ObjetViewHolder> {
    private LayoutInflater inflater;
    private List<Objet> objets;
    private ClickObjetcontroller clickObjetcontroller;


    public ObjetAdapter(LayoutInflater inflater, List<Objet> objets, ClickObjetcontroller clickObjetcontroller) {
        this.inflater = inflater;
        this.objets = objets;
        this.clickObjetcontroller = clickObjetcontroller;
    }
    public void setObjets(List<Objet> objets){
        this.objets=objets;
    }

    @Override
    public int getItemCount() {
        return objets.size();
    }


    @NonNull
    @Override
    public ObjetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRangement = inflater.inflate(R.layout.rangement_custom_view,parent,false);
        itemRangement.setOnClickListener(this.clickObjetcontroller);
        ObjetViewHolder rangementViewHolder = new ObjetViewHolder(itemRangement);
        rangementViewHolder.view = itemRangement;
        rangementViewHolder.from = (TextView) itemRangement.findViewById(R.id.tvFrom);
        rangementViewHolder.message = (TextView) itemRangement.findViewById(R.id.tvMessage);
        rangementViewHolder.date = (TextView) itemRangement.findViewById(R.id.tvDate);
        return rangementViewHolder;
    }

    @Override
    public void onBindViewHolder(ObjetViewHolder holder, int position) {
        Objet message = objets.get(position);
        holder.from.setText(message.getNom());
        holder.message.setText("volume");
        try {
            holder.date.setText(message.getRangement().toString());
        }catch(NullPointerException e){
            holder.date.setText("PAS RANGE !");
        }
        holder.position = position;
    }

    public static class ObjetViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView from, message, date;
        int position;

        public ObjetViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }
}
