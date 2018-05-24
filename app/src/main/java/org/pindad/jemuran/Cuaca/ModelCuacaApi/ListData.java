package org.pindad.jemuran.Cuaca.ModelCuacaApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListData {
    @SerializedName("nearest_area")
    private List<ListLokasi> lokasiList;
    @SerializedName("time_zone")
    private List<ListWaktu> waktuList;
    @SerializedName("current_condition")
    private List<ListCurrentWeather> currentWeatherList;
    @SerializedName("weather")
    private List<ListForecast> forecastList;

    public ListData() {
    }

    public ListData(List<ListLokasi> lokasiList, List<ListWaktu> waktuList, List<ListCurrentWeather> currentWeatherList, List<ListForecast> forecastList) {
        this.lokasiList = lokasiList;
        this.waktuList = waktuList;
        this.currentWeatherList = currentWeatherList;
        this.forecastList = forecastList;
    }

    public List<ListLokasi> getLokasiList() {
        return lokasiList;
    }

    public void setLokasiList(List<ListLokasi> lokasiList) {
        this.lokasiList = lokasiList;
    }

    public List<ListWaktu> getWaktuList() {
        return waktuList;
    }

    public void setWaktuList(List<ListWaktu> waktuList) {
        this.waktuList = waktuList;
    }

    public List<ListCurrentWeather> getCurrentWeatherList() {
        return currentWeatherList;
    }

    public void setCurrentWeatherList(List<ListCurrentWeather> currentWeatherList) {
        this.currentWeatherList = currentWeatherList;
    }

    public List<ListForecast> getForecastList() {
        return forecastList;
    }

    public void setForecastList(List<ListForecast> forecastList) {
        this.forecastList = forecastList;
    }
}
