package com.ackincolor.rangement.ui.home;

import android.util.Log;

import com.ackincolor.rangement.Database.RangementManager;
import com.ackincolor.rangement.R;
import com.ackincolor.rangement.models.Objet;
import com.ackincolor.rangement.models.Rangement;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private ArrayList<Rangement> rangements;
    private RangementManager rangementManager;
    private ArrayList<Rangement> temp = new ArrayList<Rangement>();

    private Pattern pattern;
    private Matcher matcher;

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
    public ArrayList<Rangement> search(String query){
        if(!query.equals("")){
            this.pattern = Pattern.compile(query);
            this.temp = new ArrayList<Rangement>();
            for(Rangement rangement : this.rangements){
                Log.d("Debug search","test de : "+query + "avec : " + rangement.getNom());
                this.matcher = pattern.matcher(rangement.getNom());
                if(this.matcher.find()){
                    Log.d("Debug search","OK");
                    this.temp.add(rangement);
                }
            }
            Log.d("Debug search","taille de temp : "+temp.size());
            return this.temp;
        }
        return this.rangements;
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