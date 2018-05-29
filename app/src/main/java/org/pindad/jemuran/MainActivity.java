package org.pindad.jemuran;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.pindad.jemuran.authentification.LoginActivity;
import org.pindad.jemuran.cuaca.CekLokasi;
import org.pindad.jemuran.cuaca.CuacaTempFragment;
import org.pindad.jemuran.cuaca.CuacaViewModel;
import org.pindad.jemuran.cuaca.datacuaca.GetCuacaData;
import org.pindad.jemuran.cuaca.datacuaca.GetForecastData;
import org.pindad.jemuran.cuaca.modelcuacaapi.DataWWO;
import org.pindad.jemuran.cuaca.modelcuacaapi.ListData;
import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;
import org.pindad.jemuran.home.sistem.SistemViewModel;
import org.pindad.jemuran.home.sistem.datasistem.GetSistemData;
import org.pindad.jemuran.home.status.StatusViewModel;
import org.pindad.jemuran.home.status.datastatus.GetStatusData;
import org.pindad.jemuran.sensor.SensorViewModel;
import org.pindad.jemuran.sensor.modelsensor.ListSensor;
import org.pindad.jemuran.util.MyApplication;
import org.pindad.jemuran.util.rest.ApiClient;
import org.pindad.jemuran.util.rest.ApiInterface;
import org.pindad.jemuran.home.sistem.modelsistem.ListSistem;
import org.pindad.jemuran.home.status.modelstatus.ListStatus;
import org.pindad.jemuran.home.StatusFragment;
import org.pindad.jemuran.sensor.SensorFragment;
import org.pindad.jemuran.util.BottomNavigationViewHelper;
import org.pindad.jemuran.util.sharedpreference.SaveSharedPreference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity{
    private BottomNavigationView mNavigationView;
    private FragmentManager mFragmentManager;
    private StatusFragment statusFragment;
    private SensorFragment sensorFragment;
    private CuacaTempFragment cuacaFragment;
    public String username;
    LocationManager locationManager;
    public ListData listData;
    private ArrayList<ListHourly> mListHourlies;
    private static final String TAG = MainActivity.class.getSimpleName();
    private final CompositeDisposable disposables = new CompositeDisposable();
    private ArrayList<ListStatus> mListStatus;
    private ArrayList<ListSistem> mListSistem;
    private ArrayList<ListTask> mListTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        setContentView(R.layout.activity_main);
        mNavigationView = findViewById(R.id.navigation);
        mFragmentManager = getSupportFragmentManager();
        sensorFragment = new SensorFragment();
        statusFragment = new StatusFragment();
        cuacaFragment = new CuacaTempFragment();
        mListHourlies = new ArrayList<>();
        BottomNavigationViewHelper.disableShiftMode(mNavigationView);
        mFragmentManager.beginTransaction()
                .replace(R.id.container, statusFragment)
                .commit();
        NavBotClicked();
        CekLokasi cekLokasi = new CekLokasi(this);
        cekLokasi.cek();
        setAlarm();
        getUsername();
        doSomeWork();
    }
    public void getUsername(){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), this.MODE_PRIVATE);
        username = sharedPreferences.getString(getString(R.string.username), null);
    }

    public void NavBotClicked() {
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_status:
                        mFragmentManager.beginTransaction()
                                .replace(R.id.container, statusFragment)
                                .commit();
                        return true;

                    case R.id.navigation_sensor:
                        mFragmentManager.beginTransaction()
                                .replace(R.id.container, sensorFragment)
                                .commit();
                        return true;

                    case R.id.navigation_cuaca:
                        mFragmentManager.beginTransaction()
                                .replace(R.id.container, cuacaFragment)
                                .commit();
                        return true;
                    case R.id.navigation_logout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Anda yakin ingin keluar?")
                                .setCancelable(false)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Context context = getApplicationContext();
                                        context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit().clear().commit();
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        return true;
                }
                return false;
            }
        });
    }

    private void setAlarm(){
        SistemViewModel sistemViewModel;
        StatusViewModel statusViewModel;
        CuacaViewModel cuacaViewModel;

        final Intent intent1 = new Intent(MainActivity.this, NotificationReceiver.class);
        sistemViewModel = ViewModelProviders.of(this).get(SistemViewModel.class);
        statusViewModel = ViewModelProviders.of(this).get(StatusViewModel.class);
        cuacaViewModel = ViewModelProviders.of(this).get(CuacaViewModel.class);

        statusViewModel.getListStatusMutableLiveData().observe(this, new Observer<ListStatus>() {
            @Override
            public void onChanged(@Nullable ListStatus listStatus) {
                ArrayList<ListStatus> tempListStatus = new ArrayList<>();
                tempListStatus.add(listStatus);
                mListStatus = tempListStatus;
            }
        });
        cuacaViewModel.getListForecastMutableLiveData().observe(this, new Observer<ArrayList<ListHourly>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ListHourly> listHourlies) {
                mListHourlies = listHourlies;
                intent1.putExtra("desc", mListHourlies.get(2).getWeatherDesc().get(0).getValue());
            }
        });

        for (int i=0; i<mListHourlies.size();i++){
            mListTask.add(new ListTask(mListHourlies.get(i).getTime(),mListHourlies.get(i).getWeatherDesc().get(0).getValue()));
        }

        sistemViewModel.getListSistemMutableLiveData().observe(this, new Observer<ListSistem>() {
            @Override
            public void onChanged(@Nullable ListSistem listSistem) {
                ArrayList<ListSistem> tempListSistem = new ArrayList<>();
                tempListSistem.add(listSistem);
                mListSistem = tempListSistem;
            }
        });

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY)-1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        intent1.putExtra("tes", false);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(getApplication().ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 2, pendingIntent);
    }
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
            if (mListSistem.get(0).getSistem_jemuran()){
                bool = true;
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