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
    public List<Pelicula> pelisList(String genero){
        String query;
        if(genero.equals("")){
            query = "SELECT  * FROM " + utilidades.TABLA_PELICULA;
        }else{
            query = "SELECT * FROM "+ utilidades.TABLA_PELICULA +" WHERE genero ='" + genero + "'";
        }
        List<Pelicula> peliculaLinkedList = new LinkedList<>();
         SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Pelicula pelicula;
        if (cursor.moveToFirst()){
            do{
                pelicula = new Pelicula();
                pelicula.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(utilidades.CAMPO_ID))));
                pelicula.setNombre(cursor.getString(cursor.getColumnIndex(utilidades.CAMPO_NOMBRE)));
                pelicula.setGenero(cursor.getString(cursor.getColumnIndex(utilidades.CAMPO_GENERO)));
                pelicula.setYear(cursor.getInt(cursor.getColumnIndex(utilidades.CAMPO_YEAR)));
                pelicula.setImagen(cursor.getString(cursor.getColumnIndex(utilidades.CAMPO_IMAGEN)));
                pelicula.setDescripcion(cursor.getString(cursor.getColumnIndex(utilidades.CAMPO_DESCRIPCION)));
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
           recivePelicula.setNombre(cursor.getString(cursor.getColumnIndex(utilidades.CAMPO_NOMBRE)));
           recivePelicula.setGenero(cursor.getString(cursor.getColumnIndex(utilidades.CAMPO_GENERO)));
           recivePelicula.setYear(cursor.getInt(cursor.getColumnIndex(utilidades.CAMPO_YEAR)));
           recivePelicula.setImagen(cursor.getString(cursor.getColumnIndex(utilidades.CAMPO_IMAGEN)));
           recivePelicula.setDescripcion(cursor.getString(cursor.getColumnIndex(utilidades.CAMPO_DESCRIPCION)));
        }
        return recivePelicula;

    }


    //borrado
    public void borrarPelicula(long id, Context context){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM PELICULA WHERE id = '"+id+"'");
        Toast.makeText(context, "Borrado Completado.", Toast.LENGTH_SHORT).show();
    }

    //update
    public void updatePelicula(long peliculaId, Context context, Pelicula updatePelicula){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE PELICULA SET " +
                "nombre ='"+ updatePelicula.getNombre() +
                "', genero ='" + updatePelicula.getGenero()+
                "', year ='"+ updatePelicula.getYear() +
                "', imagen ='"+ updatePelicula.getImagen() +
                "', descripcion ='"+ updatePelicula.getDescripcion() +
                "'  WHERE id=" + peliculaId + "");
        Toast.makeText(context, "Modificacion Completada.", Toast.LENGTH_SHORT).show();
    }

}
