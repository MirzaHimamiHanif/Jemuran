package org.pindad.jemuran.history;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;
import org.pindad.jemuran.history.datahistory.GetHistoryData;
import org.pindad.jemuran.history.modelhistory.ListDataTanggal;
import org.pindad.jemuran.history.modelhistory.ListHistory;
import org.pindad.jemuran.util.Interactor;

import java.util.ArrayList;

public class HistoryViewModel extends ViewModel implements Interactor<ListDataTanggal> {
    GetHistoryData getHistoryData;
    MutableLiveData<ArrayList<ListDataTanggal>> listHistoryMutableLiveData;

    public HistoryViewModel(){
        listHistoryMutableLiveData = new MutableLiveData<>();
        getHistoryData = new GetHistoryData();
        getHistoryData.registerInteractot(this);
        getHistoryData.syncHistory();
    }
    @Override
    public void onSyncData(ListDataTanggal data) {

    }

    @Override
    public void onSyncArrayData(ArrayList<ListHourly> data) {

    }

    @Override
    public void onSyncArrayHistory(ArrayList<ListDataTanggal> data) {
        listHistoryMutableLiveData.setValue(data);
    }

    @Override
    public void onFailed(ListDataTanggal error) {

    }

    public MutableLiveData<ArrayList<ListDataTanggal>> getListHistoryMutableLiveData() {
        return listHistoryMutableLiveData;
    }
}
