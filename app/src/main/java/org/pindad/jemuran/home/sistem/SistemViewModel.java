package org.pindad.jemuran.home.sistem;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;
import org.pindad.jemuran.home.sistem.datasistem.GetSistemData;
import org.pindad.jemuran.home.sistem.modelsistem.ListSistem;
import org.pindad.jemuran.util.Interactor;

import java.util.ArrayList;

public class SistemViewModel extends ViewModel implements Interactor<ListSistem> {
    GetSistemData getSistemData;
    MutableLiveData<ListSistem> listSistemMutableLiveData;

    public SistemViewModel(){
        listSistemMutableLiveData = new MutableLiveData<>();
        getSistemData = new GetSistemData();
        getSistemData.registerInteractot(this);
        getSistemData.syncSistem();
    }
    @Override
    public void onSyncData(ListSistem data) {
        listSistemMutableLiveData.setValue(data);
    }

    @Override
    public void onSyncArrayData(ArrayList<ListHourly> data) {

    }

    @Override
    public void onSyncArrayHistory(ArrayList<ListSistem> data) {

    }

    public MutableLiveData<ListSistem> getListSistemMutableLiveData() {
        return listSistemMutableLiveData;
    }

    @Override
    public void onFailed(ListSistem error) {

    }
}