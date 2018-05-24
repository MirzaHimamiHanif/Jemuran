package org.pindad.jemuran.Cuaca.ModelCuacaApi;

import com.google.gson.annotations.SerializedName;

import org.pindad.jemuran.Cuaca.ModelCuacaApi.ModelLokasi.ListAreaName;
import org.pindad.jemuran.Cuaca.ModelCuacaApi.ModelLokasi.ListCountry;
import org.pindad.jemuran.Cuaca.ModelCuacaApi.ModelLokasi.ListRegion;

import java.util.List;

public class ListLokasi {
    @SerializedName("areaName")
    private List<ListAreaName> areaName;
    @SerializedName("country")
    private List<ListCountry> country;
    @SerializedName("region")
    private List<ListRegion> region;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;

    public ListLokasi(List<ListAreaName> areaName, List<ListCountry> country, List<ListRegion> region, String latitude, String longitude) {
        this.areaName = areaName;
        this.country = country;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public List<ListAreaName> getAreaName() {
        return areaName;
    }

    public void setAreaName(List<ListAreaName> areaName) {
        this.areaName = areaName;
    }

    public List<ListCountry> getCountry() {
        return country;
    }

    public void setCountry(List<ListCountry> country) {
        this.country = country;
    }

    public List<ListRegion> getRegion() {
        return region;
    }

    public void setRegion(List<ListRegion> region) {
        this.region = region;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
