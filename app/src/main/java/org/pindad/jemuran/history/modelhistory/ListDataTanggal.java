package org.pindad.jemuran.history.modelhistory;

public class ListDataTanggal {
    private String tanggal;
    private String waktuMulai;
    private String waktuAkhir;
    private String jumlahWaktu;

    public ListDataTanggal(String tanggal, String waktuMulai, String waktuAkhir, String jumlahWaktu) {
        this.tanggal = tanggal;
        this.waktuMulai = waktuMulai;
        this.waktuAkhir = waktuAkhir;
        this.jumlahWaktu = jumlahWaktu;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktuMulai() {
        return waktuMulai;
    }

    public void setWaktuMulai(String waktuMulai) {
        this.waktuMulai = waktuMulai;
    }

    public String getWaktuAkhir() {
        return waktuAkhir;
    }

    public void setWaktuAkhir(String waktuAkhir) {
        this.waktuAkhir = waktuAkhir;
    }

    public String getJumlahWaktu() {
        return jumlahWaktu;
    }

    public void setJumlahWaktu(String jumlahWaktu) {
        this.jumlahWaktu = jumlahWaktu;
    }
}
