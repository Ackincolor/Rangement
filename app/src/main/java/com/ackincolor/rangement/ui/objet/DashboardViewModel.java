package com.ackincolor.rangement.ui.objet;

import android.util.Log;

import com.ackincolor.rangement.Database.ObjetManager;
import com.ackincolor.rangement.models.Objet;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private ArrayList<Objet> objets;
    private ObjetManager objetManager = null;

    public ObjetManager getObjetManager() {
        return objetManager;
    }

    public void setObjetManager(ObjetManager objetManager) {
        this.objetManager = objetManager;
    }

    public DashboardViewModel() {
        this.objets = new ArrayList<>();
        this.objets.add(new Objet("Objet Test"));
    }
    public void loadFromDatabase(){
        if(this.objetManager!=null){
            this.objetManager.open();
            this.objets = new ArrayList<>(this.objetManager.getAllObjets());
        }
    }

    public ArrayList<Objet> getObjets(){
        return this.objets;
    }
    public void setObjets(ArrayList<Objet> objets){
        this.objets = objets;
    }
    public void addObjet(Objet objet){
        this.objets.add(objet);
        if(this.objetManager!=null){
            this.objetManager.addObjet(objet);
        }else{
            Log.d("DEBUG DATABASE", "erreur database");
        }
    }
}