package org.pindad.jemuran.cuaca;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.pindad.jemuran.cuaca.datacuaca.GetCuacaData;
import org.pindad.jemuran.cuaca.modelcuacaapi.ListData;
import org.pindad.jemuran.sensor.datasensor.GetSensorData;
import org.pindad.jemuran.sensor.modelsensor.ListSensor;
import org.pindad.jemuran.util.Interactor;

public class CuacaViewModel extends ViewModel implements Interactor<ListData> {
    GetCuacaData getCuacaData;
    MutableLiveData<ListData> listSensorMutableLiveData;

    public CuacaViewModel(){
        listSensorMutableLiveData = new MutableLiveData<>();
        getCuacaData = new GetCuacaData();
        getCuacaData.registerInteractot(this);
        getCuacaData.syncCuaca();
    }
    public MutableLiveData<ListData> getListSensorMutableLiveData() {
        return listSensorMutableLiveData;
    }

    @Override
    public void onSyncData(ListData data) {
        listSensorMutableLiveData.setValue(data);
    }

    @Override
    public void onFailed(ListData error) {

    }
}
