package org.pindad.jemuran;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
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
import org.pindad.jemuran.cuaca.datacuaca.GetCuacaData;
import org.pindad.jemuran.cuaca.modelcuacaapi.DataWWO;
import org.pindad.jemuran.cuaca.modelcuacaapi.ListData;
import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;
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

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
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
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    public Double mLatitude, mLongitude;
    public ListData listData;
    public ArrayList<ListHourly> listHourlies;
    FirebaseDatabase database;
    public DatabaseReference myRef, myRef2;
    public ListStatus mListStatus;
    public ListSistem mListSistem;
    public SharedPreferences sharedPref;

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
        listHourlies = new ArrayList<>();
        mListStatus = new ListStatus();
        mListSistem = new ListSistem();
        BottomNavigationViewHelper.disableShiftMode(mNavigationView);
        mFragmentManager.beginTransaction()
                .replace(R.id.container, statusFragment)
                .commit();
        NavBotClicked();
        CekLokasi cekLokasi = new CekLokasi(this);
        cekLokasi.cek();
        setAlarm();
        getUsername();
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

    public void getLocation() {
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }else{
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            SaveSharedPreference.setLatitude(MyApplication.getAppContext(),String.valueOf(location.getLatitude()));
            SaveSharedPreference.setLongtitude(MyApplication.getAppContext(),String.valueOf(location.getLongitude()));
        }
    }

    private void cekLokasi(){
        sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        if(sharedPref.getString(getString(R.string.longtitude), null)!=null){
        }else {
            getLocation();
        }
        Toast.makeText(getApplicationContext(), SaveSharedPreference.getLatitude(getApplication()),Toast.LENGTH_LONG).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_LOCATION :
                getLocation();
                break;

        }
    }

    private void setAlarm(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,3);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Intent intent1 = new Intent(MainActivity.this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_HOUR * 3, pendingIntent);
    }

    public ListData getList() {
        cekLokasi();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DataWWO> call = apiService.getCurrentWeather("5b7e8f4eae6f49afb8164444182305",
                mLatitude+","+mLongitude,
                "json",
                "2",
                "no",
                "yes",
                "1",
                "yes");

        call.enqueue(new Callback<DataWWO>() {
            @Override
            public void onResponse(Call<DataWWO> call, Response<DataWWO> response) {
                int x = 0;
                Calendar rightNow = Calendar.getInstance();
                int cekJam = rightNow.get(Calendar.HOUR_OF_DAY)*100;
                ArrayList<ListHourly> tempHourly = new ArrayList<>();
                listData = new ListData();
                listData = response.body().getData();
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
                listHourlies = tempHourly;
                String cek;
                for (int i=0; i<listHourlies.size(); i++){
                    cek = listHourlies.get(i).getTime();
                    listHourlies.get(i).setTime(timeChange(cek));
                }
            }

            @Override
            public void onFailure(Call<DataWWO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return listData;
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

    private static final String TAG = MainActivity.class.getSimpleName();
    private final CompositeDisposable disposables = new CompositeDisposable();

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