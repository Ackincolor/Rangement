package com.ackincolor.rangement.controllers;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.ackincolor.rangement.MainActivity;
import com.ackincolor.rangement.ObjetViewDetail;
import com.ackincolor.rangement.RangementViewDetail;
import com.ackincolor.rangement.models.Objet;
import com.ackincolor.rangement.models.Rangement;
import com.ackincolor.rangement.ui.home.HomeViewModel;
import com.ackincolor.rangement.ui.objet.DashboardViewModel;

import androidx.recyclerview.widget.RecyclerView;

public class ClickObjetcontroller implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private DashboardViewModel dashboardViewModel;
    private Activity mainActivity;

    public ClickObjetcontroller(RecyclerView recyclerView, DashboardViewModel dashboardViewModel, Activity mainActivity){
        this.mRecyclerView = recyclerView;
        this.dashboardViewModel = dashboardViewModel;
        this.mainActivity = mainActivity;
    }
    @Override
    public void onClick(final View view) {
        int itemPosition = mRecyclerView.getChildLayoutPosition(view);
        Objet item = dashboardViewModel.getObjets().get(itemPosition);
        Log.d("Objet","Nom de l'item :"+ item.getNom());
        Intent intent = new Intent(mainActivity, ObjetViewDetail.class);
        intent.putExtra("OBJET",item);
        mainActivity.startActivity(intent);
    }
}
