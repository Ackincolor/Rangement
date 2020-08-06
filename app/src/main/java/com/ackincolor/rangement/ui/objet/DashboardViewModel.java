package com.ackincolor.rangement.ui.objet;

import com.ackincolor.rangement.models.Objet;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private ArrayList<Objet> objets;

    public DashboardViewModel() {
        this.objets = new ArrayList<>();
        this.objets.add(new Objet("Objet Test"));
    }

    public ArrayList<Objet> getObjets(){
        return this.objets;
    }
}