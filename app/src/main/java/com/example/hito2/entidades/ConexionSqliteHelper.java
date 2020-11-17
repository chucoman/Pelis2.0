package com.example.hito2.entidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.hito2.utilidades.utilidades;

public class ConexionSqliteHelper extends SQLiteOpenHelper {
    final String CREAR_TABLA_PELIDULA="CREATE TABLE pelicula (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, genero TEXT, year INTEGER, descripcion TEXT)";

    public ConexionSqliteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(utilidades.CREAR_TABLA_PELICULA);

    }
    //metodo onUpade cada vez que se ejecuta la app verifica si existe una version anterior de la db
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS pelicula");
        onCreate(db);

    }

}
