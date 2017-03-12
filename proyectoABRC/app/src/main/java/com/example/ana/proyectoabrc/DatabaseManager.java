package com.example.ana.proyectoabrc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Ana on 27/02/2017.
 */

public class DatabaseManager {

    // Diseñar tablas de la base de datos
    public static final String NOMBRE_TABLA = "usuarios";

    public static final String CN_DNI = "_dni";
    public static final String CN_NOMBRE = "nombre";
    public static final String CN_APELLIDOS = "apellidos";
    public static final String CN_TLF = "telefono";
    public static final String CN_PASSWORD = "password";

    public static final String CREATE_TABLE = "CREATE TABLE " + NOMBRE_TABLA + " ( "
            + CN_DNI + " text primary key, "
            + CN_NOMBRE + " text not null, "
            + CN_APELLIDOS + " text, "
            + CN_TLF + " text, "
            + CN_PASSWORD + " text not null );";

    public static final String PISTAS_TABLA = "pistas";

    public static final String CN_ID = "_id";
    public static final String CN_NOMPISTAS = "nombre";
    public static final String CN_DISPOIBLE = "disponible";

    public static final String CREATE_PISTAS = "CREATE TABLE " + PISTAS_TABLA + " ( "
            + CN_ID + " integer primary key, "
            + CN_NOMPISTAS + " text not null, "
            + CN_DISPOIBLE + " integer );";

    public static final String RESERVAS_TABLA = "reservas";

    public static final String CN_IDRESERVA = "_id";
    public static final String CN_CLIENTE = "cliente";
    public static final String CN_PISTA = "pista";
    public static final String CN_FECHA = "fecha";
    public static final String CN_HORA = "hora";
    public static final String CN_MAXIMO = "maximo";

    public static final String CREATE_RESERVAS = "CREATE TABLE " + RESERVAS_TABLA + " ("
            + CN_IDRESERVA + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "
            + CN_CLIENTE + " TEXT NOT NULL , "
            + CN_PISTA + " TEXT NOT NULL , "
            + CN_FECHA + " TEXT NOT NULL , "
            + CN_HORA + " TEXT NOT NULL , "
            + CN_MAXIMO + " TEXT NOT NULL );";

    private UsuariosHelper helper;
    private SQLiteDatabase db;

    public DatabaseManager(Context context) {
        helper = new UsuariosHelper(context);
        db = helper.getWritableDatabase();
    }

    public long insertar(String DNI, String nombre, String apellidos, String telefono, String clave){
        ContentValues values = new ContentValues();
        values.put(CN_DNI, DNI);
        values.put(CN_NOMBRE, nombre);
        values.put(CN_APELLIDOS, apellidos);
        values.put(CN_TLF, telefono);
        values.put(CN_PASSWORD, clave);

        return  db.insert(NOMBRE_TABLA,null,values);
    }

    public long insertarPista(int ID, String Nombre, int Disponible){
        ContentValues valores = new ContentValues();
        valores.put(CN_ID,ID);
        valores.put(CN_NOMPISTAS,Nombre);
        valores.put(CN_DISPOIBLE,Disponible);

        return db.insert(PISTAS_TABLA,null,valores);
    }

    public long insertarReserva(String dni, String nombre, String fecha, String hora, String maximo){
        ContentValues valores = new ContentValues();
        valores.put(CN_CLIENTE,dni);
        valores.put(CN_PISTA,nombre);
        valores.put(CN_FECHA,fecha);
        valores.put(CN_HORA,hora);
        valores.put(CN_MAXIMO,maximo);

        return db.insert(RESERVAS_TABLA,null,valores);
    }

    public int modificarPassword(String dni, String password){
        ContentValues values = new ContentValues();
        values.put(CN_PASSWORD, password);

        return db.update(NOMBRE_TABLA,values,CN_DNI + "=?",new String[]{dni});
    }

    public Cursor getAcceso(String dni, String password){
        return db.rawQuery("Select _dni from usuarios where _dni = '" + dni + "' and password = '" + password +"'", null);
    }

    public Cursor getDisponible(String nombre){
        int disponible = 0;
        return db.rawQuery("Select _id from pistas where nombre = '" + nombre + "' and disponible = " + disponible +"",null);
    }

    public Cursor getReserva(String nombre, String fecha, String hora){
        return db.rawQuery("Select * from reservas where pista = '" + nombre + "' and fecha = '" + fecha + "' and hora = '" + hora +"'",null);
    }


    public Cursor getBuscaReservas(String dni){
        String[] columnas = new String[]{CN_ID,CN_CLIENTE,CN_PISTA,CN_FECHA,CN_HORA};
        return db.query(RESERVAS_TABLA,columnas,CN_CLIENTE + "=?", new String[]{dni},null,null,null);
    }

    public void eliminar(String dni, String pista, String fecha){
        db.execSQL("delete from reservas where cliente = '" + dni + "' and pista = '" + pista + "' and fecha = '" + fecha + "';");
    }

}
