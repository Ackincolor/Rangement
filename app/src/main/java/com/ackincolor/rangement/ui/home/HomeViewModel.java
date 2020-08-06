package com.ackincolor.rangement.ui.home;

import com.ackincolor.rangement.models.Rangement;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private ArrayList<Rangement> rangements;

    public HomeViewModel() {
       this.rangements = new ArrayList<>();
       this.rangements.add(new Rangement());
    }
    public ArrayList<Rangement> getRangements(){
        return rangements;
    }
}