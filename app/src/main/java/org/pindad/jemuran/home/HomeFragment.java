package org.pindad.jemuran.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.pindad.jemuran.home.cek.CekViewModel;
import org.pindad.jemuran.home.sensor.SensorViewModel;
import org.pindad.jemuran.home.sensor.modelsensor.ListSensor;
import org.pindad.jemuran.home.sistem.SistemViewModel;
import org.pindad.jemuran.home.sistem.datasistem.GetSistemData;
import org.pindad.jemuran.home.sistem.modelsistem.ListSistem;
import org.pindad.jemuran.home.status.modelstatus.ListStatus;
import org.pindad.jemuran.R;
import org.pindad.jemuran.home.status.StatusViewModel;

/**
 * Created by ASUS on 11/02/2018.
 */

public class HomeFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private TextView mAtap, mKipas, mSensorHujan;
    private Switch mSistemJemuran;
    private StatusViewModel statusViewModel;
    private SistemViewModel sistemViewModel;
    private SensorViewModel sensorViewModel;
    private CekViewModel cekViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);
        mSistemJemuran = view.findViewById(R.id.switchSistemJemuran);
        mAtap = view.findViewById(R.id.statusAtap);
        mKipas = view.findViewById(R.id.statusKipas);
        mSensorHujan = view.findViewById(R.id.status_sensor_hujan);

        sensorViewModel = ViewModelProviders.of(getActivity()).get(SensorViewModel.class);
        statusViewModel = ViewModelProviders.of(getActivity()).get(StatusViewModel.class);
        sistemViewModel = ViewModelProviders.of(getActivity()).get(SistemViewModel.class);
        cekViewModel = ViewModelProviders.of(getActivity()).get(CekViewModel.class);
        mSistemJemuran.setOnCheckedChangeListener(this);
        firebaseSetUp();
        return view ;
    }

    private void firebaseSetUp(){
        statusViewModel.getListStatusMutableLiveData().observe(getActivity(), new Observer<ListStatus>() {
            @Override
            public void onChanged(@Nullable ListStatus listStatus) {
                setTextView(mAtap, listStatus.isAtap());
                setTextView(mKipas, listStatus.isKipas());
            }
        });
        sistemViewModel.getListSistemMutableLiveData().observe(getActivity(), new Observer<ListSistem>() {
            @Override
            public void onChanged(@Nullable ListSistem listSistem) {
                mSistemJemuran.setChecked(listSistem.isSistem_jemuran());
            }
        });
        sensorViewModel.getListSensorMutableLiveData().observe(getActivity(), new Observer<ListSensor>() {
            @Override
            public void onChanged(@Nullable ListSensor listSensor) {
                setTextView(mSensorHujan, listSensor.isSensor_hujan());
            }
        });
        cekViewModel.getCekMutableLiveData().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                mSistemJemuran.setChecked(aBoolean);
            }
        });
    }

    private TextView setTextView(TextView textView, boolean status){
        if (!status){
            textView.setText("Off");
        }else{
            textView.setText("On");
        }
        return textView;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        GetSistemData getSistemData = new GetSistemData();
         if (compoundButton==mSistemJemuran){
             getSistemData.pushSistem(b, "sistem_jemuran");
        }
    }
}