package com.ackincolor.rangement.ui.home;

import android.util.Log;

import com.ackincolor.rangement.Database.RangementManager;
import com.ackincolor.rangement.models.Rangement;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private ArrayList<Rangement> rangements;
    private RangementManager rangementManager;

    public HomeViewModel() {
       this.rangements = new ArrayList<>();
    }

    public RangementManager getRangementManager() {
        return rangementManager;
    }

    public void setRangementManager(RangementManager rangementManager) {
        this.rangementManager = rangementManager;
    }

    public ArrayList<Rangement> getRangements(){
        return rangements;
    }

    public void loadFromDatabase(){
        if(this.rangementManager!=null){
            this.rangementManager.open();
            this.rangements = new ArrayList<>(this.rangementManager.getAllRangements());
        }
    }
    public void addRangement(Rangement rangement){
        this.rangements.add(rangement);
        if(this.rangementManager!=null){
            this.rangementManager.addRangement(rangement);
        }else{
            Log.d("DEBUG DATABASE", "erreur database");
        }
    }
}