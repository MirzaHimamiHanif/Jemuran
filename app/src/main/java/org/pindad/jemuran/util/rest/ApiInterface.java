package org.pindad.jemuran.util.rest;

import org.pindad.jemuran.cuaca.modelcuacaapi.DataWWO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
