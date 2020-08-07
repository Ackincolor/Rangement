package com.ackincolor.rangement.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.UUID;

public class Rangement implements Parcelable {
    private String nom;
    private ArrayList<Objet> objets;
    private Double hauteur,largeur,profondeur;
    private UUID id;
    public Rangement(){
        this.id = UUID.randomUUID();
        this.nom = "Rangement";
        this.objets = new ArrayList<>();
        this.objets.add(new Objet("téléphone"));
        this.objets.add(new Objet("télévision"));
        this.largeur = this.hauteur = this.profondeur = 0.0;
    }
    public Rangement(String nom){
        this.id = UUID.randomUUID();
        this.nom = nom;
        this.objets = new ArrayList<>();
        this.largeur = this.hauteur = this.profondeur = 0.0;
    }
    public Rangement(Parcel in){
        this.id = (UUID) in.readSerializable();
        this.nom = in.readString();
        this.objets = in.readArrayList(Objet.class.getClassLoader());
        this.profondeur = in.readDouble();
        this.largeur = in.readDouble();
        this.hauteur=in.readDouble();
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

    public Double getHauteur() {
        return hauteur;
    }

    public void setHauteur(Double hauteur) {
        this.hauteur = hauteur;
    }

    public Double getLargeur() {
        return largeur;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setLargeur(Double largeur) {
        this.largeur = largeur;
    }

    public Double getProfondeur() {
        return profondeur;
    }

    public void setProfondeur(Double profondeur) {
        this.profondeur = profondeur;
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
        dest.writeSerializable(this.id);
        dest.writeString(this.nom);
        dest.writeList(this.objets);
        dest.writeDouble(this.profondeur);
        dest.writeDouble(this.largeur);
        dest.writeDouble(this.hauteur);
    }
}
