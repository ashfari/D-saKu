package com.project.d_saku.Class;

public class UserDetail {
    private String username, nik, agama, jabatan, jenisKelamin, jenisPekerjaan;
    private String namaKelurahan, namaLengkap, noKK, nomorRT, nomorRW, pendidikan;
    private String tanggalDaftar, tanggalLahir, tempatLahir;

    public UserDetail(){

    }

    public UserDetail(String username, String nik, String agama, String jabatan,
                      String jenisKelamin, String jenisPekerjaan, String namaKelurahan,
                      String namaLengkap, String noKK, String nomorRT, String nomorRW,
                      String pendidikan, String tanggalDaftar, String tanggalLahir,
                      String tempatLahir) {
        this.username = username;
        this.nik = nik;
        this.agama = agama;
        this.jabatan = jabatan;
        this.jenisKelamin = jenisKelamin;
        this.jenisPekerjaan = jenisPekerjaan;
        this.namaKelurahan = namaKelurahan;
        this.namaLengkap = namaLengkap;
        this.noKK = noKK;
        this.nomorRT = nomorRT;
        this.nomorRW = nomorRW;
        this.pendidikan = pendidikan;
        this.tanggalDaftar = tanggalDaftar;
        this.tanggalLahir = tanggalLahir;
        this.tempatLahir = tempatLahir;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getJenisPekerjaan() {
        return jenisPekerjaan;
    }

    public void setJenisPekerjaan(String jenisPekerjaan) {
        this.jenisPekerjaan = jenisPekerjaan;
    }

    public String getNamaKelurahan() {
        return namaKelurahan;
    }

    public void setNamaKelurahan(String namaKelurahan) {
        this.namaKelurahan = namaKelurahan;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getNoKK() {
        return noKK;
    }

    public void setNoKK(String noKK) {
        this.noKK = noKK;
    }

    public String getNomorRT() {
        return nomorRT;
    }

    public void setNomorRT(String nomorRT) {
        this.nomorRT = nomorRT;
    }

    public String getNomorRW() {
        return nomorRW;
    }

    public void setNomorRW(String nomorRW) {
        this.nomorRW = nomorRW;
    }

    public String getPendidikan() {
        return pendidikan;
    }

    public void setPendidikan(String pendidikan) {
        this.pendidikan = pendidikan;
    }

    public String getTanggalDaftar() {
        return tanggalDaftar;
    }

    public void setTanggalDaftar(String tanggalDaftar) {
        this.tanggalDaftar = tanggalDaftar;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }
}
