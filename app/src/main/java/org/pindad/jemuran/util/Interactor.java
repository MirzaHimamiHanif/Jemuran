package org.pindad.jemuran.util;

import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;
import org.pindad.jemuran.history.modelhistory.ListHistory;

import java.util.ArrayList;
import java.util.List;

public interface Interactor<T> {
    void onSyncData(T data);
    void onSyncArrayData(ArrayList<ListHourly> data);
    void onSyncArrayHistory(ArrayList<T> data);
    void onFailed(T error);
}
