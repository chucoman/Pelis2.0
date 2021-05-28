package com.example.hito2.Retrofit;

public class MovieModelClass {
    String id;
    String name;
    String imagen;

    public MovieModelClass() {
    }

    public MovieModelClass(String id, String name, String imagen) {
        this.id = id;
        this.name = name;
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
