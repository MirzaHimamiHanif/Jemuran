package org.pindad.jemuran.cuaca.modelcuacaapi;

import com.google.gson.annotations.SerializedName;

public class DataWWO {
    @SerializedName("data")
    private ListData data;

    public DataWWO(){

    }
    public DataWWO(ListData data) {
        this.data = data;
    }
    public ListData getData() {
        return data;
    }

    public void setData(ListData data) {
        this.data = data;
    }

}
