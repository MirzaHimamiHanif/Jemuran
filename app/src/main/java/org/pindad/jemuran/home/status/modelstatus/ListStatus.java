package org.pindad.jemuran.home.status.modelstatus;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ASUS on 12/02/2018.
 */

public class ListStatus implements Parcelable{
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

    public ListStatus(Parcel in){
        boolean[] data = new boolean[3];

        in.readBooleanArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.alarm = data[0];
        this.atap = data[1];
        this.kipas = data[2];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBooleanArray(new boolean[] {this.alarm,
                this.atap,
                this.kipas});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ListStatus createFromParcel(Parcel in) {
            return new ListStatus(in);
        }

        public ListStatus[] newArray(int size) {
            return new ListStatus[size];
        }
    };
}
