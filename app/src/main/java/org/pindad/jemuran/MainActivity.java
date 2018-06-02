package org.pindad.jemuran;

import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import org.pindad.jemuran.alarm.SetAlarm;
import org.pindad.jemuran.cuaca.CekLokasi;
import org.pindad.jemuran.cuaca.CuacaFragment;
import org.pindad.jemuran.home.HomeFragment;
import org.pindad.jemuran.sensor.SensorFragment;
import org.pindad.jemuran.setting.SettingFragment;
import org.pindad.jemuran.util.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity{
    private BottomNavigationView mNavigationView;
    private FragmentManager mFragmentManager;
    private HomeFragment homeFragment;
    private SensorFragment sensorFragment;
    private CuacaFragment cuacaFragment;
    private SettingFragment settingFragment;
    public String username;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        setContentView(R.layout.activity_main);
        mNavigationView = findViewById(R.id.navigation);
        mFragmentManager = getSupportFragmentManager();
        sensorFragment = new SensorFragment();
        homeFragment = new HomeFragment();
        cuacaFragment = new CuacaFragment();
        settingFragment = new SettingFragment();
        BottomNavigationViewHelper.disableShiftMode(mNavigationView);
        mFragmentManager.beginTransaction()
                .replace(R.id.container, homeFragment)
                .commit();
        NavBotClicked();
        CekLokasi cekLokasi = new CekLokasi(this);
        cekLokasi.cek();
        SetAlarm setAlarm = new SetAlarm();
        setAlarm.setAlarm();
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
                    case R.id.navigation_home:
                        mFragmentManager.beginTransaction()
                                .replace(R.id.container, homeFragment)
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
                    case R.id.navigation_setting:
                        mFragmentManager.beginTransaction()
                                .replace(R.id.container, settingFragment)
                                .commit();
                        return true;
                }
                return false;
            }
        });
    }
}