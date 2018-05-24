package org.pindad.jemuran.Cuaca.ModelCuacaApi.ModelForecast;

import com.google.gson.annotations.SerializedName;

import org.pindad.jemuran.Cuaca.ModelCuacaApi.ModelUmum.ListWeatherDesc;
import org.pindad.jemuran.Cuaca.ModelCuacaApi.ModelUmum.ListWeatherIcon;

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

    public ListHourly(String time, String tempC, List<ListWeatherIcon> weatherIconUrl, List<ListWeatherDesc> weatherDesc) {
        this.time = time;
        this.tempC = tempC;
        this.weatherIconUrl = weatherIconUrl;
        this.weatherDesc = weatherDesc;
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
}
