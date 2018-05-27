package org.pindad.jemuran;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String TAG = MainActivity.class.getSimpleName();
    private final CompositeDisposable disposables = new CompositeDisposable();
    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("notifId",0 );
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("Notifikasi")
                .setContentText("Waktunya Pelajaran " + intent.getStringExtra("matkul"))
                .setSound(alarmSound)
                .setAutoCancel(true);
        notificationManager.notify(id, builder.build());
        doSomeWork();
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

    private Observable<? extends Long> getObservable() {
        return Observable.just(new Long(5));
    }
    private DisposableObserver<Long> getObserver() {
        return new DisposableObserver<Long>() {
            @Override
            public void onNext(Long value) {
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
