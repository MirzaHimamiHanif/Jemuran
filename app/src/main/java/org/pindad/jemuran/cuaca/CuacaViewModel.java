package org.pindad.jemuran.cuaca;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.pindad.jemuran.cuaca.datacuaca.GetCuacaData;
import org.pindad.jemuran.cuaca.modelcuacaapi.ListData;
import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;
import org.pindad.jemuran.sensor.datasensor.GetSensorData;
import org.pindad.jemuran.sensor.modelsensor.ListSensor;
import org.pindad.jemuran.util.Interactor;

import java.util.ArrayList;

public class CuacaViewModel extends ViewModel implements Interactor<ListData> {
    GetCuacaData getCuacaData;
    MutableLiveData<ListData> listCuacaMutableLiveData;
    MutableLiveData<ArrayList<ListHourly>> listForecastMutableLiveData;

    public CuacaViewModel(){
        listCuacaMutableLiveData = new MutableLiveData<>();
        listForecastMutableLiveData = new MutableLiveData<>();
        getCuacaData = new GetCuacaData();
        getCuacaData.registerInteractot(this);
        getCuacaData.syncCuaca();
        getCuacaData.syncForecast();
    }
    public MutableLiveData<ListData> getListCuacaMutableLiveData() {
        return listCuacaMutableLiveData;
    }

    public MutableLiveData<ArrayList<ListHourly>> getListForecastMutableLiveData() {
        return listForecastMutableLiveData;
    }

    @Override
    public void onSyncData(ListData data) {
        listCuacaMutableLiveData.setValue(data);
    }

    @Override
    public void onSyncArrayData(ArrayList<ListHourly> data) {
        listForecastMutableLiveData.setValue(data);
    }

    @Override
    public void onSyncArrayHistory(ArrayList<ListData> data) {

    }

    @Override
    public void onFailed(ListData error) {

    }
}
