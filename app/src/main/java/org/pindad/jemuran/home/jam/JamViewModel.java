package org.pindad.jemuran.home.jam;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;
import org.pindad.jemuran.home.jam.datajam.GetDataJam;
import org.pindad.jemuran.home.sistem.datasistem.GetSistemData;
import org.pindad.jemuran.home.sistem.modelsistem.ListSistem;
import org.pindad.jemuran.util.Interactor;

import java.util.ArrayList;

public class JamViewModel extends ViewModel implements Interactor<Long>{
    GetDataJam getSistemData;
    MutableLiveData<Long> jamMutableLiveData;

    public JamViewModel(){
        jamMutableLiveData = new MutableLiveData<>();
        getSistemData = new GetDataJam();
        getSistemData.registerInteractot(this);
        getSistemData.syncJam();
    }


    public MutableLiveData<Long> getJamMutableLiveData() {
        return jamMutableLiveData;
    }

    @Override
    public void onSyncData(Long data) {
        jamMutableLiveData.setValue(data);
    }

    @Override
    public void onSyncArrayData(ArrayList<ListHourly> data) {

    }

    @Override
    public void onSyncArrayHistory(ArrayList<Long> data) {

    }

    @Override
    public void onFailed(Long error) {

    }
}
