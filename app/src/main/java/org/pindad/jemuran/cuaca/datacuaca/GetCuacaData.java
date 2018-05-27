package org.pindad.jemuran.cuaca.datacuaca;

import android.widget.Toast;

import org.pindad.jemuran.cuaca.modelcuacaapi.DataWWO;
import org.pindad.jemuran.cuaca.modelcuacaapi.ListData;
import org.pindad.jemuran.util.rest.ApiClient;
import org.pindad.jemuran.util.rest.ApiInterface;
import org.pindad.jemuran.util.Interactor;
import org.pindad.jemuran.util.MyApplication;
import org.pindad.jemuran.util.sharedpreference.SaveSharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCuacaData {
    private Interactor interactor;

    public void syncCuaca(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DataWWO> call = apiService.getCurrentWeather("5b7e8f4eae6f49afb8164444182305",
                SaveSharedPreference.getLatitude(MyApplication.getAppContext())
                        +","
                        +SaveSharedPreference.getLongtitude(MyApplication.getAppContext()),
                "json",
                "2",
                "no",
                "yes",
                "1",
                "yes");

        call.enqueue(new Callback<DataWWO>() {
            @Override
            public void onResponse(Call<DataWWO> call, Response<DataWWO> response) {
                ListData listData;
                listData = response.body().getData();
                interactor.onSyncData(listData);
            }

            @Override
            public void onFailure(Call<DataWWO> call, Throwable t) {
                Toast.makeText(MyApplication.getAppContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void registerInteractot(Interactor networkInteractor){
        this.interactor = networkInteractor;
    }
}
