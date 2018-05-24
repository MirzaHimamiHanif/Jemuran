package org.pindad.jemuran.Cuaca.ModelCuacaApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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
