package com.example.hito2.entidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.hito2.utilidades.utilidades;

import java.util.LinkedList;
import java.util.List;



public class ConexionSqliteHelper extends SQLiteOpenHelper {
  public static final String DATABASE_NAME = "pelicula.db";
    private static final int DATABASE_VERSION = 3;
    public static final String TABLA_PELICULA = "pelicula";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_GENERO = "genero";
    public static final String CAMPO_YEAR = "year";
    public static final String CAMPO_DESCRIPCION= "descripcion";

    public ConexionSqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLA_PELICULA +"("+
                CAMPO_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                CAMPO_NOMBRE +" TEXT, "+
                CAMPO_GENERO +" TEXT,"+
                CAMPO_YEAR +" INTEGER, "+
                CAMPO_DESCRIPCION +" TEXT);"
        );


    }

    //metodo onUpade cada vez que se ejecuta la app verifica si existe una version anterior de la db
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS pelicula");

        onCreate(db);

    }

    //insert pelicula
    public void insertPelicula(Pelicula pelicula){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre",pelicula.getNombre());
        values.put("genero",pelicula.getGenero());
        values.put("year",pelicula.getYear().toString());
        values.put("descripcion",pelicula.getDescripcion());

        //insert
        db.insert("PELICULA",null, values);
        db.close();

    }

    //buscar peliculas
    public List<Pelicula> pelisList(String genero){
        String query;
        if(genero.equals("")){
            query = "SELECT  * FROM " + TABLA_PELICULA;
        }else{
            query = "SELECT * FROM "+ TABLA_PELICULA +" WHERE genero ='" + genero + "'";
        }
        List<Pelicula> peliculaLinkedList = new LinkedList<>();
         SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Pelicula pelicula;
        if (cursor.moveToFirst()){
            do{
                pelicula = new Pelicula();
                pelicula.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(CAMPO_ID))));
                pelicula.setNombre(cursor.getString(cursor.getColumnIndex(CAMPO_NOMBRE)));
                pelicula.setGenero(cursor.getString(cursor.getColumnIndex(CAMPO_GENERO)));
                pelicula.setYear(cursor.getInt(cursor.getColumnIndex(CAMPO_YEAR)));
                pelicula.setDescripcion(cursor.getString(cursor.getColumnIndex(CAMPO_DESCRIPCION)));
                peliculaLinkedList.add(pelicula);

            } while (cursor.moveToNext());
        }
        return peliculaLinkedList;
    }


    //buscar peli unica
    public Pelicula getPelicula(Long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM PELICULA WHERE id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Pelicula recivePelicula = new Pelicula();
        if(cursor.getCount() >0){
            cursor.moveToFirst();
            recivePelicula.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
            recivePelicula.setGenero(cursor.getString(cursor.getColumnIndex("genero")));
            recivePelicula.setYear(cursor.getInt(cursor.getColumnIndex("year")));
            recivePelicula.setDescripcion(cursor.getString(cursor.getColumnIndex("descripcion")));
        }
        return recivePelicula;

    }







    //borrado
    public void borrarPelicula(long id, Context context){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM PELICULA WHERE id = '"+id+"'");
        Toast.makeText(context, "DBorrado Completado.", Toast.LENGTH_SHORT).show();
    }

    //update
    public void updatePelicula(long peliculaId, Context context, Pelicula updatePelicula){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE PELICULA SET nombre ='"+ updatePelicula.getNombre() + "', genero ='" + updatePelicula.getGenero()+ "', year ='"+ updatePelicula.getYear() + "', descripcion ='"+ updatePelicula.getDescripcion() + "'  WHERE id=" + peliculaId + "");
        Toast.makeText(context, "Modificacion Completada.", Toast.LENGTH_SHORT).show();
    }

}
