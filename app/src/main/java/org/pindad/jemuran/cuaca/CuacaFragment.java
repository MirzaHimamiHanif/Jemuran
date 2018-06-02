package org.pindad.jemuran.cuaca;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.pindad.jemuran.R;
import org.pindad.jemuran.alarm.SetAlarm;
import org.pindad.jemuran.authentification.LoginActivity;
import org.pindad.jemuran.cuaca.adapter.CuacaAdapter;
import org.pindad.jemuran.cuaca.datacuaca.GetCuacaData;
import org.pindad.jemuran.cuaca.modelcuacaapi.ListData;
import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;
import org.pindad.jemuran.util.sharedpreference.SaveSharedPreference;

import java.util.ArrayList;

/**
 * Created by ASUS on 12/02/2018.
 */

public class CuacaFragment extends Fragment implements View.OnClickListener  {
    LocationManager locationManager;
    private Double mLatitude, mLongitude;
    TextView cityField, detailsField, updatedField, temperatureField, humidityField, pressureField;
    ImageView weatherIcon;
    LinearLayout placePicker, progressBar;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ListData listData;
    private int PLACE_PICKER_REQUEST = 1;
    ArrayList<ListHourly> listHourlies;
    ProgressDialog progress_spinner;
    private CuacaViewModel cuacaViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cuaca, container, false);
        listHourlies = new ArrayList<>();
        locationManager = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);
        cityField = (TextView) view.findViewById(R.id.city_field);
        updatedField = (TextView) view.findViewById(R.id.updated_field);
        detailsField = (TextView) view.findViewById(R.id.details_field);
        weatherIcon = (ImageView) view.findViewById(R.id.weather_icon);
        placePicker = (LinearLayout) view.findViewById(R.id.click_picker);
        progressBar = (LinearLayout) view.findViewById(R.id.progressBar);
        temperatureField = (TextView) view.findViewById(R.id.current_temperature_field);
        humidityField = (TextView) view.findViewById(R.id.humidity_field);
        pressureField = (TextView) view.findViewById(R.id.pressure_field);
        listData = new ListData();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.forecast);
        mLayoutManager  = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        placePicker.setOnClickListener(this);
        cuacaViewModel = ViewModelProviders.of(getActivity()).get(CuacaViewModel.class);
        progress_spinner = new ProgressDialog(getContext());
        progress_spinner.setMessage("Loading...");
        progress_spinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress_spinner.setCancelable(false);

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
                placePicker.setVisibility(View.VISIBLE);
            }
        });
        cuacaViewModel.getListForecastMutableLiveData().observe(getActivity(), new Observer<ArrayList<ListHourly>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ListHourly> listHourlies) {
                mAdapter = new CuacaAdapter(getContext(), listHourlies);
                if(isAdded()){
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });
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

            }
        }
    }
}