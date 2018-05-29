package org.pindad.jemuran;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import org.pindad.jemuran.cuaca.CuacaViewModel;
import org.pindad.jemuran.cuaca.datacuaca.GetCuacaData;
import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;
import org.pindad.jemuran.home.sistem.SistemViewModel;
import org.pindad.jemuran.home.sistem.modelsistem.ListSistem;
import org.pindad.jemuran.home.status.StatusViewModel;
import org.pindad.jemuran.home.status.modelstatus.ListStatus;
import org.pindad.jemuran.util.MyApplication;

import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String TAG = MainActivity.class.getSimpleName();
    private final CompositeDisposable disposables = new CompositeDisposable();
    ArrayList<ListSistem> listSistem;
    ArrayList<ListStatus> listStatus;
    ArrayList<ListTask> listTask;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getBooleanExtra("tes", false)==true){

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                    .setSmallIcon(android.R.drawable.arrow_up_float)
                    .setContentTitle("Notifikasi")
                    .setContentText("Hujan")
                    .setSound(alarmSound)
                    .setAutoCancel(true);
            notificationManager.notify(0, builder.build());
        }
    }
    private void cekCuaca(){

//        sistemViewModel.getListSistemMutableLiveData().observe(, new Observer<ListSistem>() {
//            @Override
//            public void onChanged(@Nullable ListSistem listSistem) {
//                ArrayList<ListSistem> tempListSistem = new ArrayList<>();
//                tempListSistem.add(listSistem);
//            }
//        });
//        statusViewModel.getListStatusMutableLiveData().observe(this, new Observer<ListStatus>() {
//            @Override
//            public void onChanged(@Nullable ListStatus listStatus) {
//                ArrayList<ListStatus> tempListStatus = new ArrayList<>();
//                tempListStatus.add(listStatus);
//                mListStatus = tempListStatus;
//            }
//        });
//        cuacaViewModel.getListForecastMutableLiveData().observe(this, new Observer<ArrayList<ListHourly>>() {
//            @Override
//            public void onChanged(@Nullable ArrayList< ListHourly > listHourlies) {
//                mListHourlies = listHourlies;
//            }
//        });
    }
    /*
     * simple example using interval to run task at an interval of 2 sec
     * which start immediately
     */
    private void doSomeWork() {
        disposables.add(getObservable()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver()));
    }

    private Observable<? extends Boolean> getObservable() {
        Boolean bool = false;
        try {
            if (listSistem.get(0).getSistem_jemuran()==true){
                if(listStatus.get(0).getAtap()==false){
                    bool = true;
                }
            }
        }catch (Exception e){
            Log.d(TAG, " onNext : " + e);
        }
        return Observable.just(bool);
    }
    private DisposableObserver<Boolean> getObserver() {
        return new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean value) {
                GetCuacaData getCuacaData = new GetCuacaData();
                getCuacaData.syncCuaca();
                getCuacaData.syncForecast();
                Log.d(TAG, " onNext : value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, " onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, " onComplete");
            }
        };
    }
}
