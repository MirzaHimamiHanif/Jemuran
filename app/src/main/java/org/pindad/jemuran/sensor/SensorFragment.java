package org.pindad.jemuran.sensor;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.pindad.jemuran.MainActivity;
import org.pindad.jemuran.R;
import org.pindad.jemuran.sensor.modelsensor.ListSensor;
import org.pindad.jemuran.home.status.modelstatus.ListStatus;

/**
 * Created by ASUS on 11/02/2018.
 */

public class SensorFragment extends Fragment {
    ListSensor mListSensor;
    TextView mStatusGetar, mStatusHujan;
    FirebaseDatabase database;
    DatabaseReference myRef;
    SensorViewModel sensorViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sensor, container, false);
        mStatusGetar = view.findViewById(R.id.statusGetar);
        mStatusHujan = view.findViewById(R.id.statusHujan);
        sensorViewModel = ViewModelProviders.of(getActivity()).get(SensorViewModel.class);
        mListSensor = new ListSensor();

        sensorFirebase();
        return view;
    }

    private void sensorFirebase() {
        sensorViewModel.getListSensorMutableLiveData().observe(getActivity(), new Observer<ListSensor>() {
            @Override
            public void onChanged(@Nullable ListSensor listSensor) {
                setTextView(mStatusGetar,mListSensor.getSensor_getar());
                setTextView(mStatusHujan,mListSensor.getSensor_hujan());
            }
        });
    }

    public TextView setTextView(TextView textView, boolean status){
        if (status==false){
            textView.setText("OFF");
        }else{
            textView.setText("ON");
        }
        return textView;
    }
}