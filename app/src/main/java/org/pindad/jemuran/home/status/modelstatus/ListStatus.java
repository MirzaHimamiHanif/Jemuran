package org.pindad.jemuran.home.status.modelstatus;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ASUS on 12/02/2018.
 */

public class ListStatus{
    private boolean atap;
    private boolean kipas;

    public ListStatus() {
    }

    public ListStatus(boolean atap, boolean kipas) {
        this.atap = atap;
        this.kipas = kipas;
    }

    public boolean isAtap() {
        return atap;
    }

    public void setAtap(boolean atap) {
        this.atap = atap;
    }

    public boolean isKipas() {
        return kipas;
    }

    public void setKipas(boolean kipas) {
        this.kipas = kipas;
    }
}
