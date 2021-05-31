package com.example.hito2.Conexion;

public class MovieSqlite {
    private Integer id;
    private String nombre;
    private String genero;
    private Integer year;
    private String descripcion;
    private String imagen;

    public MovieSqlite(Integer id) {
        this.id = id;
    }

    public MovieSqlite(){

    }

    public MovieSqlite(Integer id, String nombre, String genero, Integer year, String descripcion, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.year = year;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public MovieSqlite(String nombre, String genero, Integer year, String descripcion, String imagen) {
        this.nombre = nombre;
        this.genero = genero;
        this.year = year;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public MovieSqlite(String nombre, String genero, Integer year, String descripcion) {

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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", genero='" + genero + '\'' +
                ", year=" + year +
                ", descripcion='" + descripcion + '\'' +
                ", imagen=" + imagen +
                '}';
    }
}
