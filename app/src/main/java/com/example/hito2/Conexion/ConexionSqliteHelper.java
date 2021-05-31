package com.example.hito2.Conexion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;



public class ConexionSqliteHelper extends SQLiteOpenHelper {

    public ConexionSqliteHelper(Context context) {
        super(context, utilidades.BD_NAME, null, utilidades.VERSION);
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

    //insert pelicula
    public long insertPelicula(String nombre, String genero, String year, String imagen, String desrip){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(utilidades.CAMPO_NOMBRE,nombre);
        values.put(utilidades.CAMPO_GENERO,genero);
        values.put(utilidades.CAMPO_YEAR,year);
        values.put(utilidades.CAMPO_IMAGEN,imagen);
        values.put(utilidades.CAMPO_DESCRIPCION,desrip);

        //insert
        long id = db.insert(utilidades.TABLA_PELICULA,utilidades.CAMPO_ID, values);
        db.close();

        return id;

    }

    //buscar peliculas
    public List<MovieSqlite> pelisList(String genero){
        String query;
        if(genero.equals("")){
            query = "SELECT  * FROM " + utilidades.TABLA_PELICULA;
        }else{
            query = "SELECT * FROM "+ utilidades.TABLA_PELICULA +" WHERE genero ='" + genero + "'";
        }
        List<MovieSqlite> movieSqliteLinkedList = new LinkedList<>();
         SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        MovieSqlite movieSqlite;
        if (cursor.moveToFirst()){
            do{
                movieSqlite = new MovieSqlite();
                movieSqlite.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(utilidades.CAMPO_ID))));
                movieSqlite.setNombre(cursor.getString(cursor.getColumnIndex(utilidades.CAMPO_NOMBRE)));
                movieSqlite.setGenero(cursor.getString(cursor.getColumnIndex(utilidades.CAMPO_GENERO)));
                movieSqlite.setYear(cursor.getInt(cursor.getColumnIndex(utilidades.CAMPO_YEAR)));
                movieSqlite.setImagen(cursor.getString(cursor.getColumnIndex(utilidades.CAMPO_IMAGEN)));
                movieSqlite.setDescripcion(cursor.getString(cursor.getColumnIndex(utilidades.CAMPO_DESCRIPCION)));
                movieSqliteLinkedList.add(movieSqlite);

            } while (cursor.moveToNext());
        }
        return movieSqliteLinkedList;
    }


    //buscar peli unica
    public MovieSqlite getPelicula(Long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM PELICULA WHERE id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        MovieSqlite reciveMovieSqlite = new MovieSqlite();
        if(cursor.getCount() >0){
            cursor.moveToFirst();
           reciveMovieSqlite.setNombre(cursor.getString(cursor.getColumnIndex(utilidades.CAMPO_NOMBRE)));
           reciveMovieSqlite.setGenero(cursor.getString(cursor.getColumnIndex(utilidades.CAMPO_GENERO)));
           reciveMovieSqlite.setYear(cursor.getInt(cursor.getColumnIndex(utilidades.CAMPO_YEAR)));
           reciveMovieSqlite.setImagen(cursor.getString(cursor.getColumnIndex(utilidades.CAMPO_IMAGEN)));
           reciveMovieSqlite.setDescripcion(cursor.getString(cursor.getColumnIndex(utilidades.CAMPO_DESCRIPCION)));
        }
        return reciveMovieSqlite;

    }


    //borrado
    public void borrarPelicula(long id, Context context){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM PELICULA WHERE id = '"+id+"'");
        Toast.makeText(context, "Borrado Completado.", Toast.LENGTH_SHORT).show();
    }

    //update
    public void updatePelicula(long peliculaId, Context context, MovieSqlite updateMovieSqlite){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE PELICULA SET " +
                "nombre ='"+ updateMovieSqlite.getNombre() +
                "', genero ='" + updateMovieSqlite.getGenero()+
                "', year ='"+ updateMovieSqlite.getYear() +
                "', imagen ='"+ updateMovieSqlite.getImagen() +
                "', descripcion ='"+ updateMovieSqlite.getDescripcion() +
                "'  WHERE id=" + peliculaId + "");
        Toast.makeText(context, "Modificacion Completada.", Toast.LENGTH_SHORT).show();
    }

}
