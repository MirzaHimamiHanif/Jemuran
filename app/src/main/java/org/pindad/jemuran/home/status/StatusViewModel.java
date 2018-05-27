package org.pindad.jemuran.home.status;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.pindad.jemuran.home.status.datastatus.GetStatusData;
import org.pindad.jemuran.util.Interactor;
import org.pindad.jemuran.home.status.modelstatus.ListStatus;

public class StatusViewModel extends ViewModel implements Interactor<ListStatus> {
    GetStatusData getStatusData;
    MutableLiveData<ListStatus> listStatusMutableLiveData;

    public StatusViewModel(){
        listStatusMutableLiveData = new MutableLiveData<>();
        getStatusData = new GetStatusData();
        getStatusData.registerInteractot(this);
        getStatusData.syncStatus();
    }
    @Override
    public void onSyncData(ListStatus data) {
        listStatusMutableLiveData.setValue(data);
    }

    public MutableLiveData<ListStatus> getListStatusMutableLiveData() {
        return listStatusMutableLiveData;
    }

    @Override
    public void onFailed(ListStatus error) {

    }
}
