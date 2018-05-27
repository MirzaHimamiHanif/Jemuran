package org.pindad.jemuran.history.ModelHistory;

public class ListHistory {
    long waktu_akhir;
    long waktu_awal;
    long waktu_rata;

    public ListHistory() {
    }

    public ListHistory(long waktu_akhir, long waktu_awal, long waktu_rata) {
        this.waktu_akhir = waktu_akhir;
        this.waktu_awal = waktu_awal;
        this.waktu_rata = waktu_rata;
    }

    public long getWaktu_akhir() {
        return waktu_akhir;
    }

    public void setWaktu_akhir(long waktu_akhir) {
        this.waktu_akhir = waktu_akhir;
    }

    public long getWaktu_awal() {
        return waktu_awal;
    }

    public void setWaktu_awal(long waktu_awal) {
        this.waktu_awal = waktu_awal;
    }

    public long getWaktu_rata() {
        return waktu_rata;
    }

    public void setWaktu_rata(long waktu_rata) {
        this.waktu_rata = waktu_rata;
    }
}
