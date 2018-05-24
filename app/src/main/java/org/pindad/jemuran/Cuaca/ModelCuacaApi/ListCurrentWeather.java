package org.pindad.jemuran.Cuaca.ModelCuacaApi;

import com.google.gson.annotations.SerializedName;

import org.pindad.jemuran.Cuaca.ModelCuacaApi.ModelUmum.ListWeatherDesc;
import org.pindad.jemuran.Cuaca.ModelCuacaApi.ModelUmum.ListWeatherIcon;

import java.util.List;

public class ListCurrentWeather {
    @SerializedName("temp_C")
    private String tempC;
    @SerializedName("weatherIconUrl")
    private List<ListWeatherIcon> weatherIconUrl;
    @SerializedName("weatherDesc")
    private List<ListWeatherDesc> weatherDesc;
    @SerializedName("humidity")
    private String humidity;
    @SerializedName("pressure")
    private String pressure;

    public ListCurrentWeather(String tempC, List<ListWeatherIcon> weatherIconUrl, List<ListWeatherDesc> weatherDesc, String humidity, String pressure) {
        this.tempC = tempC;
        this.weatherIconUrl = weatherIconUrl;
        this.weatherDesc = weatherDesc;
        this.humidity = humidity;
        this.pressure = pressure;
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

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }
}
