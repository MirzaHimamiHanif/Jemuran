package org.pindad.jemuran;

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
import android.view.ContextThemeWrapper;
import android.view.MenuItem;

import com.kwabenaberko.openweathermaplib.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.models.threehourforecast.ThreeHourForecast;

import org.pindad.jemuran.Authentification.LoginActivity;
import org.pindad.jemuran.Cuaca.CuacaFragment;
import org.pindad.jemuran.Cuaca.CuacaTempFragment;
import org.pindad.jemuran.Cuaca.ListCuaca;
import org.pindad.jemuran.Status.StatusFragment;
import org.pindad.jemuran.Sensor.SensorFragment;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mNavigationView;
    private FragmentManager mFragmentManager;
    private StatusFragment statusFragment;
    private SensorFragment sensorFragment;
    private CuacaTempFragment cuacaFragment;
    public String username;
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    private Double mLatitude, mLongitude;
    OpenWeatherMapHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationView = findViewById(R.id.navigation);
        mFragmentManager = getSupportFragmentManager();
        sensorFragment = new SensorFragment();
        statusFragment = new StatusFragment();
        cuacaFragment = new CuacaTempFragment();
        BottomNavigationViewHelper.disableShiftMode(mNavigationView);
        locationManager = (LocationManager) getApplicationContext().getSystemService(getApplicationContext().LOCATION_SERVICE);
        helper = new OpenWeatherMapHelper();
        helper.setApiKey(getString(R.string.OPEN_WEATHER_MAP_API_KEY));
        helper.setUnits(Units.METRIC);

        mFragmentManager.beginTransaction()
                .replace(R.id.container, statusFragment)
                .commit();
        NavBotClicked();
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

}
