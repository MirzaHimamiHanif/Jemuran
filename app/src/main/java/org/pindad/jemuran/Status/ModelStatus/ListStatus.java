package org.pindad.jemuran.Status.ModelStatus;

/**
 * Created by ASUS on 12/02/2018.
 */

public class ListStatus {
    private long alarm;
    private long atap;
    private long kipas;

    public ListStatus() {
    }

    public ListStatus(long alarm, long atap, long kipas) {
        this.alarm = alarm;
        this.atap = atap;
        this.kipas = kipas;
    }

    public long getAlarm() {
        return alarm;
    }

    public void setAlarm(long alarm) {
        this.alarm = alarm;
    }

    public long getAtap() {
        return atap;
    }

    public void setAtap(long atap) {
        this.atap = atap;
    }

    public long getKipas() {
        return kipas;
    }

    public void setKipas(long kipas) {
        this.kipas = kipas;
    }
}
