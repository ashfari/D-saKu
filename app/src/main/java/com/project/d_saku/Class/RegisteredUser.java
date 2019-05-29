package com.project.d_saku.Class;

public class RegisteredUser {
    private String username, password, nik, idJabatan;

    public RegisteredUser(){

    }

    public RegisteredUser(String username, String password, String nik, String idJabatan) {
        this.username = username;
        this.password = password;
        this.nik = nik;
        this.idJabatan = idJabatan;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getIdJabatan() {
        return idJabatan;
    }

    public void setIdJabatan(String idJabatan) {
        this.idJabatan = idJabatan;
    }
}
