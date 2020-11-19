package com.example.hito2.entidades;

public class Usuario {
    private Integer id;
    private String username;
    private String passwd;

    public Usuario(Integer id) {
        this.id = id;
    }

    public Usuario() {
    }

    public Usuario(Integer id, String username, String passwd) {
        this.id = id;
        this.username = username;
        this.passwd = passwd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
