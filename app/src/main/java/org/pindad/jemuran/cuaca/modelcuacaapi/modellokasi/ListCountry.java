package org.pindad.jemuran.cuaca.modelcuacaapi.modellokasi;

import com.google.gson.annotations.SerializedName;

public class ListCountry {
    @SerializedName("value")
    private String value;

    public ListCountry(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
