package org.pindad.jemuran.Cuaca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.github.mikephil.charting.data.LineRadarDataSet;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.kwabenaberko.openweathermaplib.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;
import com.kwabenaberko.openweathermaplib.models.threehourforecast.ThreeHourForecast;

import org.pindad.jemuran.Adapter.CuacaAdapter;
import org.pindad.jemuran.Cuaca.ModelCuacaApi.DataWWO;
import org.pindad.jemuran.Cuaca.ModelCuacaApi.ListCurrentWeather;
import org.pindad.jemuran.Cuaca.ModelCuacaApi.ListData;
import org.pindad.jemuran.Cuaca.ModelCuacaApi.ListForecast;
import org.pindad.jemuran.Cuaca.ModelCuacaApi.ListLokasi;
import org.pindad.jemuran.Cuaca.ModelCuacaApi.ListWaktu;
import org.pindad.jemuran.Cuaca.ModelCuacaApi.ModelForecast.ListHourly;
import org.pindad.jemuran.Cuaca.ModelCuacaApi.ModelUmum.ListWeatherIcon;
import org.pindad.jemuran.MainActivity;
import org.pindad.jemuran.R;
import org.pindad.jemuran.Rest.ApiClient;
import org.pindad.jemuran.Rest.ApiInterface;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class CuacaTempFragment extends Fragment implements View.OnClickListener {
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    private Double mLatitude, mLongitude;
    TextView cityField, detailsField, updatedField, temperatureField, humidityField, pressureField;
    ImageView weatherIcon;
    LinearLayout placePicker, progressBar;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    List<ListCurrentWeather> listCuaca;
    ListData listData;
    SharedPreferences sharedPref;
    private int PLACE_PICKER_REQUEST = 1;
    List<ListCurrentWeather> listCurrentWeathers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cuaca, container, false);
        sharedPref = getContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
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
        listCuaca = new ArrayList<>();
        listData = new ListData();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.forecast);
        mLayoutManager  = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if(sharedPref.getString(getString(R.string.longtitude), null)!=null){
            mLongitude = Double.parseDouble(sharedPref.getString(getString(R.string.longtitude), null));
            mLatitude = Double.parseDouble(sharedPref.getString(getString(R.string.latitude), null));
        }else {
            getLocation();
        }
        getList();
        placePicker.setOnClickListener(this);
        return view;
    }

    private void getLocation() {
        if(ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }else{
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try{
                mLatitude = location.getLatitude();
                mLongitude = location.getLongitude();
            }catch (Exception e){

            }
        }
    }
    private void getList() {
        ApiInterface apiService = ApiClient.getClient(mLatitude,mLongitude).create(ApiInterface.class);
        Call<DataWWO> call = apiService.getCurrentWeather("5b7e8f4eae6f49afb8164444182305",
                mLatitude+","+mLongitude,
                "json",
                "2",
                "no",
                "yes",
                "3",
                "yes");

        call.enqueue(new Callback<DataWWO>() {
            @Override
            public void onResponse(Call<DataWWO> call, Response<DataWWO> response) {
                listData = response.body().getData();
                try {
                    cityField.setText(listData.getLokasiList().get(0).getAreaName().get(0).getValue());
                }catch (Exception e){

                }
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
                ArrayList<ListHourly> listHourlies = new ArrayList<>();

                for(int i=0;i<listData.getForecastList().get(0).getHourly().size();i++){
                    listHourlies.add(listData.getForecastList().get(0).getHourly().get(i));
                }
                mAdapter = new CuacaAdapter(getContext(), listHourlies);
                if(isAdded()){
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<DataWWO> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.longtitude), mLongitude.toString());
                editor.putString(getString(R.string.latitude), mLatitude.toString());
                editor.commit();

                getList();
            }
        }
    }

}
