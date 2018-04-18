package org.pindad.jemuran;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.pindad.jemuran.Authentification.LoginActivity;
import org.pindad.jemuran.Cuaca.CuacaFragment;
import org.pindad.jemuran.Status.StatusFragment;
import org.pindad.jemuran.Sensor.SensorFragment;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mNavigationView;
    private FragmentManager mFragmentManager;
    private StatusFragment statusFragment;
    private SensorFragment sensorFragment;
    private CuacaFragment cuacaFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationView = findViewById(R.id.navigation);
        mFragmentManager = getSupportFragmentManager();
        sensorFragment = new SensorFragment();
        statusFragment = new StatusFragment();
        cuacaFragment = new CuacaFragment();

        mFragmentManager.beginTransaction()
                .replace(R.id.container, statusFragment)
                .commit();
        NavBotClicked();
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
                        Context context = getApplicationContext();
                        context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit().clear().commit();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
    }

}
