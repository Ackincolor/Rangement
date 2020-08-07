package com.ackincolor.rangement.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ackincolor.rangement.models.Rangement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RangementManager {

    private DatabaseSQLite dbm = null;
    private SQLiteDatabase db;

    public static String TABLE_NAME = "rangement";
    public static String KEY_ID_RANGEMENT = "id_rangement";
    public static String KEY_NOM_RANGEMENT = "nom_rangement";
    public static String CREATE_TABLE_RANGEMENT = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_RANGEMENT+" STRING primary key," +
            " "+KEY_NOM_RANGEMENT+" TEXT" +
            ");";

    public RangementManager(Context context){
        this.dbm = DatabaseSQLite.getInstance(context);
    }

    public void open(){
        this.db = this.dbm.getWritableDatabase();
    }
    public long addRangement(Rangement rangement) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_NOM_RANGEMENT,rangement.getNom());
        values.put(KEY_ID_RANGEMENT,rangement.getId().toString());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public void saveRangement(List<Rangement> liste){
        for(Rangement c : liste){
            addRangement(c);
        }
    }

    public List<Rangement> getAllRangements(){
        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor c = this.db.rawQuery(query,null);
        ArrayList<Rangement> liste = new ArrayList<>();
        c.moveToNext();
        while(!c.isAfterLast()){
            Rangement rangement = new Rangement(c.getString(1));
            rangement.setId(UUID.fromString(c.getString(0)));

            liste.add(rangement);
            c.moveToNext();
        }
        c.close();
        return liste;
    }
}
