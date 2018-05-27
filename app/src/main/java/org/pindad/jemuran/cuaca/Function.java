package org.pindad.jemuran.cuaca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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

import org.pindad.jemuran.adapter.CuacaAdapter;
import org.pindad.jemuran.cuaca.modelcuacaapi.DataWWO;
import org.pindad.jemuran.cuaca.modelcuacaapi.ListData;
import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;
import org.pindad.jemuran.MainActivity;
import org.pindad.jemuran.R;
import org.pindad.jemuran.util.rest.ApiClient;
import org.pindad.jemuran.util.rest.ApiInterface;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Function extends Fragment implements View.OnClickListener {
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    private Double mLatitude, mLongitude;
    TextView cityField, detailsField, updatedField, temperatureField, humidityField, pressureField;
    ImageView weatherIcon;
    LinearLayout placePicker, progressBar;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ListData listData;
    SharedPreferences sharedPref;
    private int PLACE_PICKER_REQUEST = 1;
    ArrayList<ListHourly> listHourlies;

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
        sharedPref = getContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.forecast);
        mLayoutManager  = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        setData(((MainActivity)getActivity()).listData, ((MainActivity)getActivity()).listHourlies);
        placePicker.setOnClickListener(this);
        return view;
    }

    public void getList() {
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
                listData = response.body().getData();

                Toast.makeText(getContext(), listData.getLokasiList().get(0).getAreaName().get(0).getValue(), Toast.LENGTH_LONG).show();

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
                setData(listData, listHourlies);
            }

            @Override
            public void onFailure(Call<DataWWO> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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

    public void getLocation() {
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

    private void cekLokasi(){
        sharedPref = getContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        if(sharedPref.getString(getString(R.string.longtitude), null)!=null){
            mLongitude = Double.parseDouble(sharedPref.getString(getString(R.string.longtitude), null));
            mLatitude = Double.parseDouble(sharedPref.getString(getString(R.string.latitude), null));
        }else {
            getLocation();
        }
    }
    private void setData(ListData listData, ArrayList<ListHourly> listHourlies) {
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

        mAdapter = new CuacaAdapter(getContext(), listHourlies);
        if(isAdded()){
            mRecyclerView.setAdapter(mAdapter);
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
