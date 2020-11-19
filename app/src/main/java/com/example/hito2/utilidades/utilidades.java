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


    /////////tabla usuarios

    public static String TABLA_USUARIO="usuario";
    public static final String CAMPO_ID_Usuario="id";
    public static String CAMPO_USERNAME="username";
    public static String CAMPO_PASSWORD="passwd";

    public static final String CREAR_TABLA_USUARIO="CREATE TABLE " +
            ""+TABLA_USUARIO+"("+CAMPO_ID_Usuario+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_USERNAME+" TEXT, "+CAMPO_PASSWORD+" TEXT)";


}
