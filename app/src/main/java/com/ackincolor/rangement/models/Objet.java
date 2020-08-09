package com.ackincolor.rangement.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.UUID;

public class Objet implements Parcelable {
    public enum Status {
        PRETE("Prété"),
        RANGE("Rangé"),
        ENRANGEMENT("En rangement");

        private String name = "";

        //Constructeur
        Status(String name){
            this.name = name;
        }

        public String toString(){
            return name;
        }
    }
    private UUID id;
    private String nom;
    private Double poid,prix,hauteur,largeur,profondeur;
    private UUID rangement;
    private Bitmap thumbnail;
    private Bitmap fullsizeImage;
    private Status status;
    protected boolean isSeparator = false;


    public Objet(){
        this.id = UUID.randomUUID();
        this.nom = "nom";
        this.poid = 4.0;
        this.status = Status.ENRANGEMENT;
    }
    public Objet(String nom){
        this.id = UUID.randomUUID();
        this.nom = nom;
        this.poid = 4.0;
        this.status = Status.ENRANGEMENT;
        //this.rangement = rangement;
    }
    public Objet(Parcel in){
        this.id = (UUID) in.readSerializable();
        this.nom = in.readString();
        this.poid = in.readDouble();
        this.rangement = (UUID) in.readSerializable();
        this.status = Status.valueOf(in.readString());
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

    public boolean isSeparator(){
        return this.isSeparator;
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

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public String getStatus(){
        return this.status.toString();
    }

    public String getStatusName(){
        return this.status.name();
    }

    public void setStatus(String name){
        if(name!=null)
        this.status = Status.valueOf(name);
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Bitmap getFullsizeImage() {
        return fullsizeImage;
    }

    public void setFullsizeImage(Bitmap fullsizeImage) {
        this.fullsizeImage = fullsizeImage;
    }

    public void setRangement(UUID rangement){
        if(rangement!=null){
            this.status=Status.RANGE;
            this.rangement = rangement;
            Log.d("DEBUG SEPARATOR","les rangement est :"+rangement.toString());
        }
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
        dest.writeString(this.status.name());
    }
}
