package org.pindad.jemuran.home.cek;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;
import org.pindad.jemuran.home.cek.datacek.GetCekJemuran;
import org.pindad.jemuran.util.Interactor;

import java.util.ArrayList;

public class CekViewModel extends ViewModel implements Interactor<Boolean> {
    GetCekJemuran getCekJemuran;
    MutableLiveData<Boolean> cekMutableLiveData;

    public CekViewModel(){
        cekMutableLiveData = new MutableLiveData<>();
        getCekJemuran = new GetCekJemuran();
        getCekJemuran.registerInteractot(this);
        getCekJemuran.syncCek();
    }

    public MutableLiveData<Boolean> getCekMutableLiveData() {
        return cekMutableLiveData;
    }

    @Override
    public void onSyncData(Boolean data) {
        cekMutableLiveData.setValue(data);
    }

    @Override
    public void onSyncArrayData(ArrayList<ListHourly> data) {

    }

    @Override
    public void onSyncArrayHistory(ArrayList<Boolean> data) {

    }

    @Override
    public void onFailed(Boolean error) {

    }
}
