package com.ackincolor.rangement.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseSQLite extends SQLiteOpenHelper {
    private static DatabaseSQLite sInstance;
    private static final String DATABASE_NAME = "db.sqlite";
    private static final int DATABASE_VERSION = 3;

    public static synchronized DatabaseSQLite getInstance(Context context) {
        if (sInstance == null) { sInstance = new DatabaseSQLite(context); }
        return sInstance;
    }
    private DatabaseSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ObjetManager.CREATE_TABLE_OBJET);
        db.execSQL(RangementManager.CREATE_TABLE_RANGEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ObjetManager.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+RangementManager.TABLE_NAME);
        onCreate(db);
    }
}
