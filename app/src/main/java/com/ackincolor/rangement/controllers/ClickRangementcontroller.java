package com.ackincolor.rangement.controllers;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.ackincolor.rangement.MainActivity;
import com.ackincolor.rangement.RangementViewDetail;
import com.ackincolor.rangement.models.Rangement;
import com.ackincolor.rangement.ui.home.HomeViewModel;

import androidx.recyclerview.widget.RecyclerView;

public class ClickRangementcontroller implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private HomeViewModel homeViewModel;
    private MainActivity mainActivity;
    public ClickRangementcontroller(RecyclerView recyclerView, HomeViewModel homeViewModel, MainActivity mainActivity){
        this.mRecyclerView = recyclerView;
        this.homeViewModel = homeViewModel;
        this.mainActivity = mainActivity;
    }
    @Override
    public void onClick(final View view) {
        int itemPosition = mRecyclerView.getChildLayoutPosition(view);
        Rangement item = homeViewModel.getRangements().get(itemPosition);
        Log.d("rangement","Nom de l'item :"+ item.getNom());
        Intent intent = new Intent(mainActivity, RangementViewDetail.class);
        intent.putExtra("RANGEMENT",item);
        mainActivity.startActivity(intent);
    }
}
