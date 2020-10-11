package com.ackincolor.rangement.ui.objet;

import android.util.Log;

import com.ackincolor.rangement.Database.ObjetManager;
import com.ackincolor.rangement.models.Objet;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private ArrayList<Objet> objets;
    private ObjetManager objetManager = null;
    private ArrayList<Objet> temp = new ArrayList<Objet>();

    private Pattern pattern;
    private Matcher matcher;

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
    public boolean  deleteObjet(Objet objet){
        if(objetManager.deleteObjet(objet))
        {
            this.objets.remove(objet);
            return true;
        }else{
            return false;
        }
    }
    public ArrayList<Objet> search(String query){
        if(!query.equals("")){
            this.pattern = Pattern.compile(query);
            this.temp = new ArrayList<Objet>();
            for(Objet objet : this.objets){
                Log.d("Debug search","test de : "+query + "avec : " + objet.getNom());
                this.matcher = pattern.matcher(objet.getNom());
                if(this.matcher.find() || objet.isSeparator()){
                    Log.d("Debug search","OK");
                    this.temp.add(objet);
                }
            }
            Log.d("Debug search","taille de temp : "+temp.size());
            return this.temp;
        }
        return this.objets;
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