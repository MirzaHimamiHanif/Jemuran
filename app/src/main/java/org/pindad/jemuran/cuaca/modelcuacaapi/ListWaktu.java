package org.pindad.jemuran.cuaca.modelcuacaapi;

import com.google.gson.annotations.SerializedName;

public class ListWaktu {
    @SerializedName("localtime")
    private String localTime;

    public ListWaktu(String localTime) {
        this.localTime = localTime;
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }
}
