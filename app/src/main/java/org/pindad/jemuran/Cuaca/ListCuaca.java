package org.pindad.jemuran.Cuaca;

import android.text.Spanned;

public class ListCuaca {
    private String date;
    private Spanned weather;
    private String temperature;

    public ListCuaca(){

    }
    public ListCuaca(String date, Spanned weather, String temperature) {
        this.date = date;
        this.weather = weather;
        this.temperature = temperature;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Spanned getWeather() {
        return weather;
    }

    public void setWeather(Spanned weather) {
        this.weather = weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
