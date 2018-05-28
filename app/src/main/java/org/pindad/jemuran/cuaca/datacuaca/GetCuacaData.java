package org.pindad.jemuran.cuaca.datacuaca;

import android.content.Context;
import android.widget.Toast;

import org.pindad.jemuran.R;
import org.pindad.jemuran.cuaca.modelcuacaapi.DataWWO;
import org.pindad.jemuran.cuaca.modelcuacaapi.ListData;
import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;
import org.pindad.jemuran.util.rest.ApiClient;
import org.pindad.jemuran.util.rest.ApiInterface;
import org.pindad.jemuran.util.Interactor;
import org.pindad.jemuran.util.MyApplication;
import org.pindad.jemuran.util.sharedpreference.SaveSharedPreference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
                try {
                    interactor.onSyncData(response.body().getData());
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<DataWWO> call, Throwable t) {
                Toast.makeText(MyApplication.getAppContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void syncForecast(){
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
                ListData listData = response.body().getData();
                int x = 0;
                Calendar rightNow = Calendar.getInstance();
                int cekJam = rightNow.get(Calendar.HOUR_OF_DAY)*100;
                ArrayList<ListHourly> tempHourly = new ArrayList<>();
                for (int i=0; i<listData.getForecastList().size(); i++ ){
                    for(int j=0;j<listData.getForecastList().get(i).getHourly().size();j++){
                        if(i==0){
                            String t = listData.getForecastList().get(i).getHourly().get(j).getTime();
                            int tempNum = Integer.parseInt(t);
                            if (tempNum>cekJam){
                                x++;
                                tempHourly.add(listData.getForecastList().get(i).getHourly().get(j));
                            }
                        }else {
                            tempHourly.add(listData.getForecastList().get(i).getHourly().get(j));
                            x++;
                            if (x==25){
                                break;
                            }
                        }
                    }
                }

                String cek;
                for (int i=0; i<tempHourly.size(); i++){
                    cek = tempHourly.get(i).getTime();
                    tempHourly.get(i).setTime(timeChange(cek));
                }
                interactor.onSyncArrayData(tempHourly);
            }

            @Override
            public void onFailure(Call<DataWWO> call, Throwable t) {
                Toast.makeText(MyApplication.getAppContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private String timeChange(String time){
        if (time.equals("0")){
            time = "00:00";
        }else if (time.length()==3){
            time = new StringBuilder().append("0").append(time.charAt(0)).append(":").append(time.charAt(1)).append(time.charAt(2)).toString();
        }else if (time.length()==4){
            time = new StringBuilder().append(time.charAt(0)).append(time.charAt(1)).append(":").append(time.charAt(2)).append(time.charAt(3)).toString();
        }
        return time;
    }

    public void registerInteractot(Interactor networkInteractor){
        this.interactor = networkInteractor;
    }
}
