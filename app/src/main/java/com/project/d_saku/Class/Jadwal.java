package com.project.d_saku.Class;

public class Jadwal {
    private String idJadwal, dariUsername, kepentinganJadwal, waktuJadwal, keJabatan, statusJadwal;

    public Jadwal() {
    }

    public Jadwal(String idJadwal, String dariUsername, String kepentinganJadwal, String waktuJadwal, String keJabatan, String statusJadwal) {
        this.idJadwal = idJadwal;
        this.dariUsername = dariUsername;
        this.kepentinganJadwal = kepentinganJadwal;
        this.waktuJadwal = waktuJadwal;
        this.keJabatan = keJabatan;
        this.statusJadwal = statusJadwal;
    }

    public String getIdJadwal() {
        return idJadwal;
    }

    public void setIdJadwal(String idJadwal) {
        this.idJadwal = idJadwal;
    }

    public String getDariUsername() {
        return dariUsername;
    }

    public void setDariUsername(String dariUsername) {
        this.dariUsername = dariUsername;
    }

    public String getKepentinganJadwal() {
        return kepentinganJadwal;
    }

    public void setKepentinganJadwal(String kepentinganJadwal) {
        this.kepentinganJadwal = kepentinganJadwal;
    }

    public String getWaktuJadwal() {
        return waktuJadwal;
    }

    public void setWaktuJadwal(String waktuJadwal) {
        this.waktuJadwal = waktuJadwal;
    }

    public String getKeJabatan() {
        return keJabatan;
    }

    public void setKeJabatan(String keJabatan) {
        this.keJabatan = keJabatan;
    }

    public String getStatusJadwal() {
        return statusJadwal;
    }

    public void setStatusJadwal(String statusJadwal) {
        this.statusJadwal = statusJadwal;
    }
}
