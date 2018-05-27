package org.pindad.jemuran.util.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "http://api.worldweatheronline.com/premium/v1/";
            ;

    static final String BASE_URL_FINAL = "";
//    public static final String BASE_URL = "http://api.pindad.com/as/";

    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
