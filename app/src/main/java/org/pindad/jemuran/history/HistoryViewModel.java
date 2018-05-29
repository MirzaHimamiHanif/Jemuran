package org.pindad.jemuran.history;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;
import org.pindad.jemuran.history.datahistory.GetHistoryData;
import org.pindad.jemuran.history.modelhistory.ListHistory;
import org.pindad.jemuran.history.modelhistory.ListHistory;
import org.pindad.jemuran.util.Interactor;

import java.util.ArrayList;

public class HistoryViewModel extends ViewModel implements Interactor<ListHistory> {
    GetHistoryData getHistoryData;
    MutableLiveData<ArrayList<ListHistory>> listHistoryMutableLiveData;

    public HistoryViewModel(){
        listHistoryMutableLiveData = new MutableLiveData<>();
        getHistoryData = new GetHistoryData();
        getHistoryData.registerInteractot(this);
        getHistoryData.syncHistory();
    }
    @Override
    public void onSyncData(ListHistory data) {

    }

    @Override
    public void onSyncArrayData(ArrayList<ListHourly> data) {

    }

    @Override
    public void onSyncArrayHistory(ArrayList<ListHistory> data) {
        listHistoryMutableLiveData.setValue(data);
    }

    @Override
    public void onFailed(ListHistory error) {

    }

    public MutableLiveData<ArrayList<ListHistory>> getListHistoryMutableLiveData() {
        return listHistoryMutableLiveData;
    }
}
