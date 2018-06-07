package org.pindad.jemuran.home.sensor;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import org.pindad.jemuran.MainActivity;
import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;
import org.pindad.jemuran.home.sensor.datasensor.GetSensorData;
import org.pindad.jemuran.home.sensor.modelsensor.ListSensor;
import org.pindad.jemuran.util.Interactor;
import org.pindad.jemuran.util.MyApplication;

import java.util.ArrayList;

public class SensorViewModel extends ViewModel implements Interactor<ListSensor> {
    GetSensorData getSensorData;
    MutableLiveData<ListSensor> listSensorMutableLiveData;

    public SensorViewModel(){
        listSensorMutableLiveData = new MutableLiveData<>();
        getSensorData = new GetSensorData();
        getSensorData.registerInteractot(this);
        getSensorData.syncSensor();
    }
    @Override
    public void onSyncData(ListSensor data) {
        listSensorMutableLiveData.setValue(data);
        if(data.isSensor_hujan()){
            setNotification();
        }
    }

    @Override
    public void onSyncArrayData(ArrayList<ListHourly> data) {

    }

    @Override
    public void onSyncArrayHistory(ArrayList<ListSensor> data) {

    }

    public MutableLiveData<ListSensor> getListSensorMutableLiveData() {
        return listSensorMutableLiveData;
    }

    @Override
    public void onFailed(ListSensor error) {

    }

    private void setNotification(){
        Intent repeatingIntent = new Intent(MyApplication.getAppContext(), MainActivity.class);
        repeatingIntent.putExtra("getId", 1);
        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.getAppContext(),1, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) MyApplication.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MyApplication.getAppContext(), "1")
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("Notifikasi")
                .setContentText("Hujan")
                .setSound(alarmSound)
                .setAutoCancel(true);
        notificationManager.notify(1, builder.build());
    }
}
