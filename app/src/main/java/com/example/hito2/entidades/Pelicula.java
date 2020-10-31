package com.example.hito2.entidades;

public class Pelicula {
    private Integer id;
    private String nombre;
    private String genero;
    private Integer year;
    private String descripcion;

    public Pelicula(Integer id) {
        this.id = id;
    }

    public Pelicula(Integer id, String nombre, String genero, Integer year, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.year = year;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }



}
