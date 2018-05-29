package org.pindad.jemuran.home.sistem.modelsistem;

import android.os.Parcel;
import android.os.Parcelable;

import org.pindad.jemuran.home.status.modelstatus.ListStatus;

public class ListSistem implements Parcelable{
    private boolean sistem_anti_maling;
    private boolean sistem_jemuran;

    public ListSistem() {
    }

    public ListSistem(boolean sistem_anti_maling, boolean sistem_jemuran) {
        this.sistem_anti_maling = sistem_anti_maling;
        this.sistem_jemuran = sistem_jemuran;
    }

    public boolean getSistem_anti_maling() {
        return sistem_anti_maling;
    }

    public void setSistem_anti_maling(boolean sistem_anti_maling) {
        this.sistem_anti_maling = sistem_anti_maling;
    }

    public boolean getSistem_jemuran() {
        return sistem_jemuran;
    }

    public void setSistem_jemuran(boolean sistem_jemuran) {
        this.sistem_jemuran = sistem_jemuran;
    }
    public ListSistem(Parcel in){
        boolean[] data = new boolean[2];

        in.readBooleanArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.sistem_anti_maling = data[0];
        this.sistem_jemuran = data[1];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBooleanArray(new boolean[] {this.sistem_anti_maling,
                this.sistem_jemuran});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ListSistem createFromParcel(Parcel in) {
            return new ListSistem(in);
        }

        public ListSistem[] newArray(int size) {
            return new ListSistem[size];
        }
    };
}
