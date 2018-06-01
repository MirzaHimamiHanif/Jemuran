package org.pindad.jemuran.cuaca;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import org.pindad.jemuran.R;
import org.pindad.jemuran.util.MyApplication;
import org.pindad.jemuran.util.sharedpreference.SaveSharedPreference;

public class CekLokasi {
    static final int REQUEST_LOCATION = 1;
    Activity activity;

    public CekLokasi(Activity activity){
        this.activity = activity;
    }
    public void getLocation() {
        if(ActivityCompat.checkSelfPermission(MyApplication.getAppContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyApplication.getAppContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }else{
            LocationManager locationManager = (LocationManager) MyApplication
                    .getAppContext()
                    .getSystemService(MyApplication.getAppContext().LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            SaveSharedPreference.setLatitude(MyApplication.getAppContext(), new Double(location.getLatitude()).toString());
            SaveSharedPreference.setLongtitude(MyApplication.getAppContext(),String.valueOf(location.getLongitude()));
        }
    }

    public void cek(){
        if(SaveSharedPreference.getLatitude(MyApplication.getAppContext())!=null){
        }else {
            getLocation();
        }
    }

}
