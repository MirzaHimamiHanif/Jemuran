package org.pindad.jemuran.cuaca.modelcuacaapi.modelumum;

import com.google.gson.annotations.SerializedName;

public class ListWeatherIcon {
    @SerializedName("value")
    private String value;

    public ListWeatherIcon(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
