package com.ackincolor.rangement.ui.objet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ackincolor.rangement.R;
import com.ackincolor.rangement.controllers.ClickObjetcontroller;
import com.ackincolor.rangement.controllers.ClickRangementcontroller;
import com.ackincolor.rangement.models.Objet;
import com.ackincolor.rangement.models.ObjetSeparator;
import com.ackincolor.rangement.models.Rangement;
import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ObjetAdapter extends RecyclerView.Adapter<ObjetAdapter.BaseViewHolder> implements SwipeActionAdapter.SwipeActionListener{
    private LayoutInflater inflater;
    private List<Objet> objets;
    private ClickObjetcontroller clickObjetcontroller;

    public static final int COMMON = 1;
    public static final int SEPARATOR = 2;

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

    @Override
    public void onSwipe(int[] position, SwipeDirection[] direction) {

    }
    @Override
    public boolean hasActions(int position, SwipeDirection direction){
        if(direction.isLeft()) return true;
        if(direction.isRight()) return true;
        return false;
    }
    @Override
    public boolean shouldDismiss(int position, SwipeDirection direction){
        return false;
        //return direction == SwipeDirection.DIRECTION_NORMAL_LEFT;
    }

    @NonNull
    @Override
    public BaseViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==COMMON) {
            View itemRangement = inflater.inflate(R.layout.rangement_custom_view, parent, false);
            itemRangement.setOnClickListener(this.clickObjetcontroller);
            ObjetViewHolder rangementViewHolder = new ObjetViewHolder(itemRangement);
            rangementViewHolder.view = itemRangement;
            rangementViewHolder.from = (TextView) itemRangement.findViewById(R.id.tvFrom);
            rangementViewHolder.message = (TextView) itemRangement.findViewById(R.id.tvMessage);
            rangementViewHolder.image = (ImageView) itemRangement.findViewById(R.id.thumbnailRangement);
            return rangementViewHolder;
        }else{
            View itemSeparator = inflater.inflate(R.layout.separator_view, parent, false);
            SeparatorViewHolder separatorViewHolder = new SeparatorViewHolder(itemSeparator);
            separatorViewHolder.nomRangement = itemSeparator.findViewById(R.id.separator_name);
            return separatorViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (getItemViewType(position) == COMMON) {
            ObjetViewHolder holder2 = new ObjetViewHolder(holder.itemView);
            holder2.view = holder.itemView;
            holder2.from = (TextView) holder.itemView.findViewById(R.id.tvFrom);
            holder2.message = (TextView) holder.itemView.findViewById(R.id.tvMessage);
            holder2.image = (ImageView) holder.itemView.findViewById(R.id.thumbnailRangement);
            Objet message = objets.get(position);
            holder2.from.setText(message.getNom());
            try {
                holder2.message.setText(message.getStatus());
            }catch(NullPointerException e){
                holder2.message.setText("PAS RANGE !");
            }
            holder2.position = position;
            holder = holder2;
        } else {
            ObjetSeparator message = (ObjetSeparator) objets.get(position);
            SeparatorViewHolder separatorViewHolder = new SeparatorViewHolder(holder.itemView);
            separatorViewHolder.nomRangement = holder.itemView.findViewById(R.id.separator_name);
            separatorViewHolder.nomRangement.setText(message.getRangementName());
            holder = separatorViewHolder;
        }
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public static class ObjetViewHolder extends BaseViewHolder {
        View view;
        TextView from, message;
        ImageView image;
        int position;

        public ObjetViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }
    public static class SeparatorViewHolder extends BaseViewHolder{
        TextView nomRangement;
        public SeparatorViewHolder(View itemView){
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Objet message = objets.get(position);
        if(message.isSeparator())
            return SEPARATOR;
        return COMMON;

    }
}
