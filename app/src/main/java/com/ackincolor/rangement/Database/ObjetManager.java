package com.ackincolor.rangement.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ackincolor.rangement.models.Objet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ObjetManager {

    private DatabaseSQLite dbm = null;
    private SQLiteDatabase db;
    public static String TABLE_NAME = "objet";
    public static String KEY_ID_OBJET = "id_objet";
    public static String KEY_NOM_OBJET = "nom_objet";
    public static String KEY_RANGEMENT_ID = "rangement";
    public static String CREATE_TABLE_OBJET = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_OBJET+" STRING primary key," +
            " "+KEY_NOM_OBJET+" TEXT," +
            " "+KEY_RANGEMENT_ID+" STRING" +
            ");";

    public ObjetManager(Context context){
        this.dbm = DatabaseSQLite.getInstance(context);
    }
    public void open(){
        this.db = this.dbm.getWritableDatabase();
    }

    public long addObjet(Objet objet) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_NOM_OBJET,objet.getNom());
        values.put(KEY_ID_OBJET,objet.getId().toString());
        if(objet.getRangement()!=null)
        values.put(KEY_RANGEMENT_ID,objet.getRangement().toString());
        else
        values.put(KEY_RANGEMENT_ID,"NULL");

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public void saveObjets(List<Objet> liste){
        for(Objet c : liste){
            addObjet(c);
        }
    }

    public List<Objet> getAllObjets(){
        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor c = this.db.rawQuery(query,null);
        ArrayList<Objet> liste = new ArrayList<>();
        c.moveToNext();
        while(!c.isAfterLast()){
            Objet objet = new Objet(c.getString(1));
            objet.setId(UUID.fromString(c.getString(0)));
            try {
                objet.setRangement(UUID.fromString(c.getString(2)));
            }catch(IllegalArgumentException e){
                Log.d("DEBUG","Objet sans UUID de rangement ");
                objet.setRangement(null);
            }
            liste.add(objet);
            c.moveToNext();
        }
        c.close();
        return liste;
    }
    public List<Objet> getAllObjetsForRangmeent(UUID rangementId){
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+ KEY_RANGEMENT_ID+" = '" +rangementId.toString()+"'";
        Cursor c = this.db.rawQuery(query,null);
        ArrayList<Objet> liste = new ArrayList<>();
        c.moveToNext();
        while(!c.isAfterLast()){
            Objet objet = new Objet(c.getString(1));
            objet.setId(UUID.fromString(c.getString(0)));
            try {
                objet.setRangement(UUID.fromString(c.getString(2)));
            }catch(IllegalArgumentException e){
                Log.d("DEBUG","Objet sans UUID de rangement ");
                objet.setRangement(null);
            }
            liste.add(objet);
            c.moveToNext();
        }
        c.close();
        return liste;
    }
}
