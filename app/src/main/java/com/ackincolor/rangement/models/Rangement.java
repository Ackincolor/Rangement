package com.ackincolor.rangement.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Rangement implements Parcelable {
    private String nom;
    private ArrayList<Objet> objets;
    private Double hauteur,largeur,profondeur;
    public Rangement(){
        this.nom = "Rangmeent";
        this.objets = new ArrayList<>();
        this.largeur = this.hauteur = this.profondeur = 0.0;
    }
    public Rangement(String nom){
        this.nom = nom;
        this.objets = new ArrayList<>();
        this.largeur = this.hauteur = this.profondeur = 0.0;
    }
    public Rangement(Parcel in){
        this.nom = in.readString();
        this.objets = in.readArrayList(null);
    }
    public void AjouterObjet(Objet obj){
        this.objets.add(obj);
    }
    public Objet retirerObjet(Objet obj){
        return this.objets.remove(this.objets.indexOf(obj));
    }
    public ArrayList<Objet> voirListeObjet(){
        return this.objets;
    }
    public Double getVolume(){
        return this.hauteur*this.largeur*this.profondeur;
    }

    public String getNom(){
        return this.nom;
    }
    public String getVolumeTexte(){
        return this.getVolume().toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Rangement> CREATOR
            = new Parcelable.Creator<Rangement>() {
        public Rangement createFromParcel(Parcel in) {
            return new Rangement(in);
        }

        public Rangement[] newArray(int size) {
            return new Rangement[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nom);
        dest.writeList(this.objets);
    }
}
