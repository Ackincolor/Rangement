package com.ackincolor.rangement.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class Objet implements Parcelable {
    private UUID id;
    private String nom;
    private Double poid,prix,hauteur,largeur,profondeur;
    private UUID rangement;


    public Objet(){
        this.id = UUID.randomUUID();
        this.nom = "nom";
        this.poid = 4.0;
    }
    public Objet(String nom){
        this.id = UUID.randomUUID();
        this.nom = nom;
        this.poid = 4.0;
        //this.rangement = rangement;
    }
    public Objet(Parcel in){
        this.id = (UUID) in.readSerializable();
        this.nom = in.readString();
        this.poid = in.readDouble();
        this.rangement = (UUID) in.readSerializable();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public void setRangement(UUID rangement){
        this.rangement = rangement;
    }

    public UUID getRangement() {
        return rangement;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.id);
        dest.writeString(this.nom);
        dest.writeDouble(poid);
        dest.writeSerializable(this.rangement);
    }
}
