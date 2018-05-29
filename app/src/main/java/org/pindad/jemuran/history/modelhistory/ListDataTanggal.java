package org.pindad.jemuran.history.modelhistory;

public class ListDataTanggal {
    private String tanggal;
    private String waktu;

    public ListDataTanggal(String tanggal, String waktu) {
        this.tanggal = tanggal;
        this.waktu = waktu;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
