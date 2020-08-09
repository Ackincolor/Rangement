package com.ackincolor.rangement.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.ackincolor.rangement.models.Rangement;

import java.io.ByteArrayOutputStream;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RangementManager {

    private DatabaseSQLite dbm = null;
    private SQLiteDatabase db;

    public static String TABLE_NAME = "rangement";
    public static String KEY_ID_RANGEMENT = "id_rangement";
    public static String KEY_NOM_RANGEMENT = "nom_rangement";
    public static String KEY_FULL_IMAGE = "full_image";
    public static String KEY_THUMBNAIL_IMAGE = "thumbnail_image";
    public static String CREATE_TABLE_RANGEMENT = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_RANGEMENT+" STRING primary key," +
            " "+KEY_NOM_RANGEMENT+" TEXT," +
            " "+KEY_FULL_IMAGE+" BLOB,"+
            " "+KEY_THUMBNAIL_IMAGE+" BLOB"+
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

        if(rangement.getFullsizeImage()!=null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            rangement.getFullsizeImage().compress(Bitmap.CompressFormat.JPEG, 100, bos);
            values.put(KEY_FULL_IMAGE, bos.toByteArray());
        }

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public void saveRangement(List<Rangement> liste){
        for(Rangement c : liste){
            addRangement(c);
        }
    }

    public void updateRangement(Rangement rangement){
        ContentValues values = new ContentValues();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
        rangement.getFullsizeImage().compress(Bitmap.CompressFormat.JPEG, 100, bos);
        rangement.getThumbnail().compress(Bitmap.CompressFormat.JPEG, 100, bos2);
        values.put(KEY_NOM_RANGEMENT,rangement.getNom());
        values.put(KEY_ID_RANGEMENT,rangement.getId().toString());
        values.put(KEY_FULL_IMAGE,bos.toByteArray());
        values.put(KEY_THUMBNAIL_IMAGE,bos2.toByteArray());

        db.update(TABLE_NAME,values,KEY_ID_RANGEMENT+"='"+rangement.getId().toString()+"'",null);
    }

    public List<Rangement> getAllRangements(){
        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor c = this.db.rawQuery(query,null);
        ArrayList<Rangement> liste = new ArrayList<>();
        c.moveToNext();
        while(!c.isAfterLast()){
            Rangement rangement = new Rangement(c.getString(1));
            rangement.setId(UUID.fromString(c.getString(0)));
            byte[] image = c.getBlob(2);
            if(image!=null) {
                rangement.setFullsizeImage(BitmapFactory.decodeByteArray(image, 0, image.length));
            }
            byte[] thumbnail = c.getBlob(3);
            if(thumbnail!=null) {
                rangement.setThumbnail(BitmapFactory.decodeByteArray(thumbnail, 0, thumbnail.length));
            }
            liste.add(rangement);
            c.moveToNext();
        }
        c.close();
        return liste;
    }
    public Rangement getRangements(String ID){
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_RANGEMENT+"='"+ID+"'";
        Cursor c = this.db.rawQuery(query,null);
        c.moveToNext();
        while(!c.isAfterLast()){
            Rangement rangement = new Rangement(c.getString(1));
            rangement.setId(UUID.fromString(c.getString(0)));
            byte[] image = c.getBlob(2);
            if(image!=null) {
                rangement.setFullsizeImage(BitmapFactory.decodeByteArray(image, 0, image.length));
            }
            byte[] thumbnail = c.getBlob(3);
            if(thumbnail!=null) {
                rangement.setThumbnail(BitmapFactory.decodeByteArray(thumbnail, 0, thumbnail.length));
            }
            c.moveToNext();
            return rangement;
        }
        c.close();
        return null;
    }
}
