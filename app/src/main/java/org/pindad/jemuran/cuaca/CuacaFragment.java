package org.pindad.jemuran.cuaca;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.pindad.jemuran.R;
import org.pindad.jemuran.alarm.SetAlarm;
import org.pindad.jemuran.authentification.LoginActivity;
import org.pindad.jemuran.cuaca.adapter.CuacaAdapter;
import org.pindad.jemuran.cuaca.datacuaca.GetCuacaData;
import org.pindad.jemuran.cuaca.modelcuacaapi.ListData;
import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;
import org.pindad.jemuran.util.MyApplication;
import org.pindad.jemuran.util.sharedpreference.SaveSharedPreference;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ASUS on 12/02/2018.
 */

public class CuacaFragment extends Fragment implements View.OnClickListener  {
    private Double mLatitude, mLongitude;
    TextView cityField, detailsField, updatedField, temperatureField, humidityField, pressureField, mTextForecast;
    ImageView weatherIcon;
    CardView placePicker;
    LinearLayout linearLayout;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    private int PLACE_PICKER_REQUEST = 1;
    private CuacaViewModel cuacaViewModel;
    int tempNum, tempNum2;
    String hujan;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cuaca, container, false);
        cityField = view.findViewById(R.id.city_field);
        updatedField = view.findViewById(R.id.updated_field);
        detailsField = view.findViewById(R.id.details_field);
        weatherIcon = view.findViewById(R.id.weather_icon);
        placePicker = view.findViewById(R.id.click_picker);
        temperatureField = view.findViewById(R.id.current_temperature_field);
        humidityField = view.findViewById(R.id.humidity_field);
        pressureField = view.findViewById(R.id.pressure_field);
        mRecyclerView = view.findViewById(R.id.forecast);
        mTextForecast = view.findViewById(R.id.text_forecast);
        linearLayout = view.findViewById(R.id.layout_cuaca);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child(SaveSharedPreference.getUserName(MyApplication.getAppContext()));

        placePicker.setOnClickListener(this);
        cuacaViewModel = ViewModelProviders.of(getActivity()).get(CuacaViewModel.class);
        setData();
        return view;
    }
    private void setData() {
        CekLokasi cekLokasi = new CekLokasi(getActivity());
        cekLokasi.cek();
        cuacaViewModel.getListCuacaMutableLiveData().observe(getActivity(), new Observer<ListData>() {
            @Override
            public void onChanged(@Nullable ListData listData) {
                cityField.setText(listData.getLokasiList().get(0).getAreaName().get(0).getValue());
                updatedField.setText(listData.getWaktuList().get(0).getLocalTime());
                detailsField.setText(listData.getCurrentWeatherList().get(0).getWeatherDesc().get(0).getValue());
                if(isAdded()){
                    Glide.with(getContext())
                            .load(listData.getCurrentWeatherList().get(0).getWeatherIconUrl().get(0).getValue())
                            .into(weatherIcon);
                }
                temperatureField.setText(listData.getCurrentWeatherList().get(0).getTempC() + "°C");
                humidityField.setText("Humidity : " + listData.getCurrentWeatherList().get(0).getHumidity() + "%");
                pressureField.setText("Pressure : " + listData.getCurrentWeatherList().get(0).getPressure() + " mb");
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
        cuacaViewModel.getListForecastMutableLiveData().observe(getActivity(), new Observer<ArrayList<ListHourly>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ListHourly> listHourlies) {
                mAdapter = new CuacaAdapter(getContext(), listHourlies);
                mLayoutManager  = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                setTextForecast(listHourlies);
                if(isAdded()){
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });
    }

    private void setTextForecast(ArrayList<ListHourly> listHourlies){
        try{
            hujan = listHourlies.get(0).getTime();
            tempNum = 0;
            tempNum2 = 0;
            if (Integer.parseInt(listHourlies.get(0).getWeatherCode())>150){
                for (int i=1; i<listHourlies.size(); i++){
                    if (Integer.parseInt(listHourlies.get(i).getWeatherCode())<150){
                        break;
                    }
                    tempNum++;
                }
            }else{
                for (int i=1; i<listHourlies.size(); i++){
                    if (Integer.parseInt(listHourlies.get(i).getWeatherCode())>150){
                        break;
                    }
                    tempNum2++;
                }
            }
            String text = "";
            if (tempNum!=0){
                text = "Hujan selama " + (tempNum +1) + " jam dari jam " +hujan;
            }else {
                text = "Cuaca cerah selama " + (tempNum2 +1) + " jam dari jam " +hujan;
            }
            mTextForecast.setText(text);
            mTextForecast.invalidate();
        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == placePicker){
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try {
                //menjalankan place picker
                startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                // check apabila <a title="Solusi Tidak Bisa Download Google Play Services di Android" href="http://www.twoh.co/2014/11/solusi-tidak-bisa-download-google-play-services-di-android/" target="_blank">Google Play Services tidak terinstall</a> di HP
            } catch (GooglePlayServicesRepairableException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST){
            if (resultCode == getActivity().RESULT_OK){
                Place place = PlacePicker.getPlace(getActivity(),data);
                mLongitude = place.getLatLng().longitude;
                mLatitude = place.getLatLng().latitude;
                SaveSharedPreference.setLongtitude(getContext(), mLongitude.toString());
                SaveSharedPreference.setLatitude(getContext(), mLatitude.toString());
                GetCuacaData getCuacaData = new GetCuacaData();
                getCuacaData.syncCuaca();
                getCuacaData.syncForecast();
                getCuacaData.registerInteractot(cuacaViewModel);
                SetAlarm setAlarm = new SetAlarm();
                setAlarm.setAlarm();
                mTextForecast.invalidate();
            }
        }
    }
}