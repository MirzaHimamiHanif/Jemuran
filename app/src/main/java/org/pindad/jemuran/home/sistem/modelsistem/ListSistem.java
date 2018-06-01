package org.pindad.jemuran.home.sistem.modelsistem;

import android.os.Parcel;
import android.os.Parcelable;

import org.pindad.jemuran.home.status.modelstatus.ListStatus;

public class ListSistem{
    private boolean sistem_jemuran;

    public ListSistem() {
    }

    public ListSistem(boolean sistem_jemuran) {
        this.sistem_jemuran = sistem_jemuran;
    }

    public boolean isSistem_jemuran() {
        return sistem_jemuran;
    }

    public void setSistem_jemuran(boolean sistem_jemuran) {
        this.sistem_jemuran = sistem_jemuran;
    }
}
