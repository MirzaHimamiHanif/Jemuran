package org.pindad.jemuran.alarm;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.pindad.jemuran.MainActivity;
import org.pindad.jemuran.cuaca.datacuaca.GetCuacaData;
import org.pindad.jemuran.cuaca.modelcuacaapi.DataWWO;
import org.pindad.jemuran.cuaca.modelcuacaapi.ListData;
import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;
import org.pindad.jemuran.history.modelhistory.ListHistory;
import org.pindad.jemuran.home.sistem.modelsistem.ListSistem;
import org.pindad.jemuran.home.status.modelstatus.ListStatus;
import org.pindad.jemuran.util.MyApplication;
import org.pindad.jemuran.util.rest.ApiClient;
import org.pindad.jemuran.util.rest.ApiInterface;
import org.pindad.jemuran.util.sharedpreference.SaveSharedPreference;

import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        cekCuaca();
    }

    private void cekCuaca(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DataWWO> call = apiService.getCurrentWeather("5b7e8f4eae6f49afb8164444182305",
                SaveSharedPreference.getLatitude(MyApplication.getAppContext())
                        +","
                        +SaveSharedPreference.getLongtitude(MyApplication.getAppContext()),
                "json",
                "2",
                "no",
                "yes",
                "1",
                "yes");

        call.enqueue(new Callback<DataWWO>() {
            @Override
            public void onResponse(Call<DataWWO> call, Response<DataWWO> response) {
                ArrayList<ListHourly> tempHourly = new ArrayList<>();
                ListData listData = response.body().getData();
                int x = 0;
                Calendar rightNow = Calendar.getInstance();
                int cekJam = rightNow.get(Calendar.HOUR_OF_DAY)*100;
                for (int i=0; i<listData.getForecastList().size(); i++ ){
                    for(int j=0;j<listData.getForecastList().get(i).getHourly().size();j++){
                        if(i==0){
                            String t = listData.getForecastList().get(i).getHourly().get(j).getTime();
                            int tempNum = Integer.parseInt(t);
                            if (tempNum>cekJam){
                                x++;
                                tempHourly.add(listData.getForecastList().get(i).getHourly().get(j));
                            }
                        }else {
                            tempHourly.add(listData.getForecastList().get(i).getHourly().get(j));
                            x++;
                            if (x==25){
                                break;
                            }
                        }
                    }
                }

                String cek;
                for (int i=0; i<tempHourly.size(); i++){
                    cek = tempHourly.get(i).getTime();
                    tempHourly.get(i).setTime(timeChange(cek));
                }
                String hujan;
                int tempNum = 0;
                hujan = tempHourly.get(0).getTime();
                if (Integer.parseInt(tempHourly.get(0).getWeatherCode())>150){
                    for (int i=1; i<tempHourly.size(); i++){
                        tempNum++;
                        if (Integer.parseInt(tempHourly.get(i).getWeatherCode())<150){
                            break;
                        }
                    }
                }
                String text = "";
                if (tempNum!=0){
                    text = "Hujan selama " + tempNum + " jam dari jam " +hujan;
                    final FirebaseDatabase database;
                    final DatabaseReference myRef;
                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference().child(SaveSharedPreference.getUserName(MyApplication.getAppContext()));
                    myRef.child("jam").setValue(tempNum +1);

                }else {
                    text = "Cuaca cerah sampai jam " + hujan;
                }
                Intent repeatingIntent = new Intent(MyApplication.getAppContext(), NotificationView.class);
                repeatingIntent.putExtra("getId", 0);
                repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.getAppContext(),0, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationManager notificationManager = (NotificationManager) MyApplication.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MyApplication.getAppContext(), "0")
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(android.R.drawable.arrow_up_float)
                        .setContentTitle("Notifikasi")
                        .setContentText(text)
                        .setSound(alarmSound)
                        .setAutoCancel(true);
                notificationManager.notify(0, builder.build());
            }

            @Override
            public void onFailure(Call<DataWWO> call, Throwable t) {
                Toast.makeText(MyApplication.getAppContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private String timeChange(String time){
        if (time.equals("0")){
            time = "00:00";
        }else if (time.length()==3){
            time = new StringBuilder().append("0").append(time.charAt(0)).append(":").append(time.charAt(1)).append(time.charAt(2)).toString();
        }else if (time.length()==4){
            time = new StringBuilder().append(time.charAt(0)).append(time.charAt(1)).append(":").append(time.charAt(2)).append(time.charAt(3)).toString();
        }
        return time;
    }
}
