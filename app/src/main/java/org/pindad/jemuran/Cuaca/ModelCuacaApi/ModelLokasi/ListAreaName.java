package org.pindad.jemuran.Cuaca.ModelCuacaApi.ModelLokasi;

import com.google.gson.annotations.SerializedName;

public class ListAreaName {
    @SerializedName("value")
    private String value;

    public ListAreaName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
