package org.pindad.jemuran.cuaca.modelcuacaapi.modelumum;

import com.google.gson.annotations.SerializedName;

public class ListWeatherDesc {
    @SerializedName("value")
    private String value;

    public ListWeatherDesc(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
