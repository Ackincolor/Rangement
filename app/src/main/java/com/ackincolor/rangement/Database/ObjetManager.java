package com.ackincolor.rangement.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.ackincolor.rangement.models.Objet;
import com.ackincolor.rangement.models.ObjetSeparator;

import java.io.ByteArrayOutputStream;
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
    public static String KEY_STATUS = "status";
    public static String KEY_FULL_IMAGE = "full_image";
    public static String KEY_THUMBNAIL_IMAGE = "thumbnail_image";
    public static String CREATE_TABLE_OBJET = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_OBJET+" STRING primary key," +
            " "+KEY_NOM_OBJET+" TEXT," +
            " "+KEY_RANGEMENT_ID+" STRING," +
            " "+KEY_FULL_IMAGE+" BLOB,"+
            " "+KEY_THUMBNAIL_IMAGE+" BLOB,"+
            " "+KEY_STATUS+" STRING" +
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
        values.put(KEY_STATUS,objet.getStatusName());
        if(objet.getFullsizeImage()!=null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            objet.getFullsizeImage().compress(Bitmap.CompressFormat.JPEG, 100, bos);
            values.put(KEY_FULL_IMAGE, bos.toByteArray());
        }

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public void saveObjets(List<Objet> liste){
        for(Objet c : liste){
            addObjet(c);
        }
    }

    public List<Objet> getAllObjets(){
        String query = "SELECT * FROM "+TABLE_NAME + " ORDER BY "+KEY_RANGEMENT_ID + " ASC";
        Cursor c = this.db.rawQuery(query,null);
        ArrayList<Objet> liste = new ArrayList<>();
        c.moveToNext();
        UUID lastRangement = null;
        while(!c.isAfterLast()){
            //verification du nom du rangement
            String uuidtest = c.getString(2);
            UUID idTest;


            if(!uuidtest.equals("NULL")){
                idTest = UUID.fromString(uuidtest);
                if(!idTest.equals(lastRangement))
                {
                    //création du séparateur
                    String query2 = "SELECT * FROM "+RangementManager.TABLE_NAME+" WHERE "+RangementManager.KEY_ID_RANGEMENT+"='"+idTest+"'";
                    Cursor c2 = this.db.rawQuery(query2,null);
                    c2.moveToNext();
                    String nomObjet = "";
                    while(!c2.isAfterLast()){
                        nomObjet = c2.getString(1);
                        Log.d("DEBUG","Rangement trouvé : "+nomObjet+" UUID :"+idTest);
                        c2.moveToNext();
                    }
                    ObjetSeparator objetSeparator = new ObjetSeparator(nomObjet);
                    liste.add(objetSeparator);
                    lastRangement = idTest;
                }
            }else{
                ObjetSeparator objetSeparator = new ObjetSeparator("AUCUN");
                liste.add(objetSeparator);
            }
            //
            Objet objet = new Objet(c.getString(1));
            objet.setId(UUID.fromString(c.getString(0)));
            try {
                objet.setRangement(UUID.fromString(c.getString(2)));
            }catch(IllegalArgumentException e){
                Log.d("DEBUG","Objet sans UUID de rangement ");
                objet.setRangement(null);
            }
            byte[] image = c.getBlob(3);
            if(image!=null) {
                objet.setFullsizeImage(BitmapFactory.decodeByteArray(image, 0, image.length));
            }
            byte[] thumbnail = c.getBlob(4);
            if(thumbnail!=null) {
                objet.setThumbnail(BitmapFactory.decodeByteArray(thumbnail, 0, thumbnail.length));
            }
            String nameStatus = c.getString(5);
            objet.setStatus(nameStatus);
            liste.add(objet);
            c.moveToNext();
        }
        c.close();
        return liste;
    }
    public void updateObjet(Objet objet){
        ContentValues values = new ContentValues();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
        objet.getFullsizeImage().compress(Bitmap.CompressFormat.JPEG, 100, bos);
        objet.getThumbnail().compress(Bitmap.CompressFormat.JPEG, 100, bos2);
        values.put(KEY_NOM_OBJET,objet.getNom());
        values.put(KEY_ID_OBJET,objet.getId().toString());
        values.put(KEY_FULL_IMAGE,bos.toByteArray());
        values.put(KEY_THUMBNAIL_IMAGE,bos2.toByteArray());
        values.put(KEY_STATUS,objet.getStatusName());

        db.update(TABLE_NAME,values,KEY_ID_OBJET+"='"+objet.getId().toString()+"'",null);
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
            byte[] image = c.getBlob(2);
            if(image!=null) {
                objet.setFullsizeImage(BitmapFactory.decodeByteArray(image, 0, image.length));
            }
            byte[] thumbnail = c.getBlob(3);
            if(thumbnail!=null) {
                objet.setThumbnail(BitmapFactory.decodeByteArray(thumbnail, 0, thumbnail.length));
            }
            objet.setStatus(c.getString(5));
            liste.add(objet);
            c.moveToNext();
        }
        c.close();
        return liste;
    }

    public Objet getObjet(String ID){
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_OBJET+"='"+ID+"'";
        Cursor c = this.db.rawQuery(query,null);
        c.moveToNext();
        while(!c.isAfterLast()){
            Objet objet = new Objet(c.getString(1));
            objet.setId(UUID.fromString(c.getString(0)));
            byte[] image = c.getBlob(3);
            if(image!=null) {
                objet.setFullsizeImage(BitmapFactory.decodeByteArray(image, 0, image.length));
            }
            byte[] thumbnail = c.getBlob(4);
            if(thumbnail!=null) {
                objet.setThumbnail(BitmapFactory.decodeByteArray(thumbnail, 0, thumbnail.length));
            }
            objet.setStatus(c.getString(5));
            c.moveToNext();
            return objet;
        }
        c.close();
        return null;
    }
}
