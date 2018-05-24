package org.pindad.jemuran.Rest;

import org.pindad.jemuran.Cuaca.ModelCuacaApi.DataWWO;
import org.pindad.jemuran.Cuaca.ModelCuacaApi.ListCurrentWeather;
import org.pindad.jemuran.Cuaca.ModelCuacaApi.ListLokasi;
import org.pindad.jemuran.Cuaca.ModelCuacaApi.ListWaktu;
import org.pindad.jemuran.Cuaca.ModelCuacaApi.ModelForecast.ListHourly;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("weather.ashx?")
    Call<DataWWO> getCurrentWeather(
            @Field("key") String key,
            @Field("q") String langlong,
            @Field("format") String format,
            @Field("num_of_days") String num_of_days,
            @Field("mca") String mca,
            @Field("includelocation") String includelocation,
            @Field("tp") String tp,
            @Field("showlocaltime") String showlocaltime);
}
