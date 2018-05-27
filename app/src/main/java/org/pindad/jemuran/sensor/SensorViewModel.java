package org.pindad.jemuran.sensor;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.pindad.jemuran.sensor.datasensor.GetSensorData;
import org.pindad.jemuran.sensor.modelsensor.ListSensor;
import org.pindad.jemuran.util.Interactor;

public class SensorViewModel extends ViewModel implements Interactor<ListSensor> {
    GetSensorData getSensorData;
    MutableLiveData<ListSensor> listSensorMutableLiveData;

    public SensorViewModel(){
        listSensorMutableLiveData = new MutableLiveData<>();
        getSensorData = new GetSensorData();
        getSensorData.registerInteractot(this);
        getSensorData.syncSensor();
    }
    @Override
    public void onSyncData(ListSensor data) {
        listSensorMutableLiveData.setValue(data);
    }

    public MutableLiveData<ListSensor> getListSensorMutableLiveData() {
        return listSensorMutableLiveData;
    }

    @Override
    public void onFailed(ListSensor error) {

    }
}
