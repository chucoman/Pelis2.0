package com.example.hito2.utilidades;

public class utilidades {
    //Constantes campos tabla pelicula
    public static final String TABLA_PELICULA="pelicula";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_NOMBRE="nombre";
    public static final String CAMPO_GENERO="genero";
    public static final String CAMPO_YEAR="year";
    public static final String CAMPO_DESCRIPCION="descripcion";

    public static final String CREAR_TABLA_PELICULA="CREATE TABLE " +
            ""+TABLA_PELICULA+"("+CAMPO_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_NOMBRE+" TEXT, "+CAMPO_GENERO+" TEXT," +
            " "+CAMPO_YEAR+" INTEGER, "+CAMPO_DESCRIPCION+" TEXT)";



}
