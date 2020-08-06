package com.ackincolor.rangement.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Objet implements Parcelable {
    private String nom;
    private Double poid,prix,hauteur,largeur,profondeur;

    public Objet(){
        this.nom = "nom";
        this.poid = 4.0;
    }
    public Objet(String nom){
        this.nom = nom;
        this.poid = 4.0;
    }
    public Objet(Parcel in){
        this.nom = in.readString();
        this.poid = in.readDouble();
    }

    public String getNom(){
        return this.nom;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Objet> CREATOR
            = new Parcelable.Creator<Objet>() {
        public Objet createFromParcel(Parcel in) {
            return new Objet(in);
        }

        public Objet[] newArray(int size) {
            return new Objet[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nom);
        dest.writeDouble(poid);
    }
}
