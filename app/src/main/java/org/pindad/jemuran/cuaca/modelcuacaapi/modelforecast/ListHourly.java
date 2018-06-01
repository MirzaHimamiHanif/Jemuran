package org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast;

import com.google.gson.annotations.SerializedName;

import org.pindad.jemuran.cuaca.modelcuacaapi.modelumum.ListWeatherDesc;
import org.pindad.jemuran.cuaca.modelcuacaapi.modelumum.ListWeatherIcon;

import java.util.List;

public class ListHourly {
    @SerializedName("time")
    private String time;
    @SerializedName("tempC")
    private String tempC;
    @SerializedName("weatherIconUrl")
    private List<ListWeatherIcon> weatherIconUrl;
    @SerializedName("weatherDesc")
    private List<ListWeatherDesc> weatherDesc;
    @SerializedName("weatherCode")
    private String weatherCode;

    public ListHourly(String time, String tempC, List<ListWeatherIcon> weatherIconUrl, List<ListWeatherDesc> weatherDesc, String weatherCode) {
        this.time = time;
        this.tempC = tempC;
        this.weatherIconUrl = weatherIconUrl;
        this.weatherDesc = weatherDesc;
        this.weatherCode = weatherCode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTempC() {
        return tempC;
    }

    public void setTempC(String tempC) {
        this.tempC = tempC;
    }

    public List<ListWeatherIcon> getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public void setWeatherIconUrl(List<ListWeatherIcon> weatherIconUrl) {
        this.weatherIconUrl = weatherIconUrl;
    }

    public List<ListWeatherDesc> getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(List<ListWeatherDesc> weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }
}
