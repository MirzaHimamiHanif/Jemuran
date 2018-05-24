package org.pindad.jemuran.Cuaca.ModelCuacaApi.ModelUmum;

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
