package org.pindad.jemuran.cuaca.datacuaca;

import android.widget.Toast;

import org.pindad.jemuran.cuaca.modelcuacaapi.ListData;
import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;
import org.pindad.jemuran.util.Interactor;
import org.pindad.jemuran.util.InteractorArrayList;

import java.util.ArrayList;
import java.util.Calendar;

public class GetForecastData implements Interactor<ListData>{
    private InteractorArrayList interactor;

    public void syncForecast(){
        int x = 0;
    }

    @Override
    public void onSyncData(ListData data) {

    }

    @Override
    public void onFailed(ListData error) {

    }
}
