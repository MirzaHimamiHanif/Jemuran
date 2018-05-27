package org.pindad.jemuran.home.status.modelstatus;

/**
 * Created by ASUS on 12/02/2018.
 */

public class ListStatus {
    private boolean alarm;
    private boolean atap;
    private boolean kipas;

    public ListStatus() {
    }

    public ListStatus(boolean alarm, boolean atap, boolean kipas) {
        this.alarm = alarm;
        this.atap = atap;
        this.kipas = kipas;
    }

    public boolean getAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

    public boolean getAtap() {
        return atap;
    }

    public void setAtap(boolean atap) {
        this.atap = atap;
    }

    public boolean getKipas() {
        return kipas;
    }

    public void setKipas(boolean kipas) {
        this.kipas = kipas;
    }
}
