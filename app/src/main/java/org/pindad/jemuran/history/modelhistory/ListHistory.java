package org.pindad.jemuran.history.modelhistory;

public class ListHistory {
    private String tanggal;
    private long kelembapan;
    private long suhu;

    public ListHistory() {
    }

    public ListHistory(String tanggal, long kelembapan, long suhu) {
        this.tanggal = tanggal;
        this.kelembapan = kelembapan;
        this.suhu = suhu;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public long getKelembapan() {
        return kelembapan;
    }

    public void setKelembapan(long kelembapan) {
        this.kelembapan = kelembapan;
    }

    public long getSuhu() {
        return suhu;
    }

    public void setSuhu(long suhu) {
        this.suhu = suhu;
    }
}
