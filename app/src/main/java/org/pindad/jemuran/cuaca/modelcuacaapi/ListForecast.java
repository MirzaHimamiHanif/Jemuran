package org.pindad.jemuran.cuaca.modelcuacaapi;

import com.google.gson.annotations.SerializedName;

import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;

import java.util.List;

public class ListForecast {
    @SerializedName("date")
    private String date;
    @SerializedName("hourly")
    private List<ListHourly> hourly;

    public ListForecast(String date, List<ListHourly> hourly) {
        this.date = date;
        this.hourly = hourly;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ListHourly> getHourly() {
        return hourly;
    }

    public void setHourly(List<ListHourly> hourly) {
        this.hourly = hourly;
    }
}
