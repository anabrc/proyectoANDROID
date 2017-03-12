package com.example.ana.proyectoabrc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ana on 27/02/2017.
 */

public class UsuariosHelper extends SQLiteOpenHelper{

    public static final String BDPOLIDEPORTIVO = "dbPolideportivo.sqlite";
    public static final int DB_VERSION = 1;

    public UsuariosHelper(Context context) {
        super(context, BDPOLIDEPORTIVO, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseManager.CREATE_TABLE);
        db.execSQL(DatabaseManager.CREATE_PISTAS);
        db.execSQL(DatabaseManager.CREATE_RESERVAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
