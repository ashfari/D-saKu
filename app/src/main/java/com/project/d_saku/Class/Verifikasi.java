package com.project.d_saku.Class;

public class Verifikasi {
    private String dariVerifikasi, idVerifikasi, keVerifikasi, status, tanggalKirim, tanggalTerima, dariNik;

    public  Verifikasi(){

    }

    public Verifikasi(String dariVerifikasi, String idVerifikasi, String keVerifikasi, String status, String tanggalKirim, String tanggalTerima, String dariNik) {
        this.dariVerifikasi = dariVerifikasi;
        this.idVerifikasi = idVerifikasi;
        this.keVerifikasi = keVerifikasi;
        this.status = status;
        this.tanggalKirim = tanggalKirim;
        this.tanggalTerima = tanggalTerima;
        this.dariNik = dariNik;
    }

    public String getDariVerifikasi() {
        return dariVerifikasi;
    }

    public void setDariVerifikasi(String dariVerifikasi) {
        this.dariVerifikasi = dariVerifikasi;
    }

    public String getIdVerifikasi() {
        return idVerifikasi;
    }

    public void setIdVerifikasi(String idVerifikasi) {
        this.idVerifikasi = idVerifikasi;
    }

    public String getKeVerifikasi() {
        return keVerifikasi;
    }

    public void setKeVerifikasi(String keVerifikasi) {
        this.keVerifikasi = keVerifikasi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggalKirim() {
        return tanggalKirim;
    }

    public void setTanggalKirim(String tanggalKirim) {
        this.tanggalKirim = tanggalKirim;
    }

    public String getTanggalTerima() {
        return tanggalTerima;
    }

    public void setTanggalTerima(String tanggalTerima) {
        this.tanggalTerima = tanggalTerima;
    }

    public String getDariNik() {
        return dariNik;
    }

    public void setDariNik(String dariNik) {
        this.dariNik = dariNik;
    }
}
