package org.pindad.jemuran.cuaca;

import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.kwabenaberko.openweathermaplib.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;
import com.kwabenaberko.openweathermaplib.models.threehourforecast.ThreeHourForecast;

import org.pindad.jemuran.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by ASUS on 12/02/2018.
 */

public class CuacaFragment extends Fragment implements View.OnClickListener  {
    static final int REQUEST_LOCATION = 1;
    private Double mLatitude, mLongitude;
    OpenWeatherMapHelper helper;
    TextView cityField, detailsField, weatherIcon, updatedField, temperatureField, humidityField, pressureField;
    ImageButton placePicker;
    Typeface weatherFont;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    long sunrise, sunset;
    ArrayList<ListCuaca> listCuaca;
    private int PLACE_PICKER_REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cuaca, container, false);
        weatherFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/weathericons-regular-webfont.ttf");
        helper = new OpenWeatherMapHelper();
        helper.setApiKey(getString(R.string.OPEN_WEATHER_MAP_API_KEY));
        helper.setUnits(Units.METRIC);
        cityField = (TextView) view.findViewById(R.id.city_field);
        updatedField = (TextView) view.findViewById(R.id.updated_field);
        detailsField = (TextView) view.findViewById(R.id.details_field);
        weatherIcon = (TextView) view.findViewById(R.id.weather_icon);
        temperatureField = (TextView) view.findViewById(R.id.current_temperature_field);
        humidityField = (TextView) view.findViewById(R.id.humidity_field);
        pressureField = (TextView) view.findViewById(R.id.pressure_field);
        placePicker = (ImageButton) view.findViewById(R.id.place_picker);
        weatherIcon.setTypeface(weatherFont);
        listCuaca = new ArrayList<>();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.forecast);
        mLayoutManager  = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        getLocation();
        placePicker.setOnClickListener(this);
        return view;
    }

    private void getLocation() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);
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
            helper.getCurrentWeatherByGeoCoordinates(mLatitude, mLongitude, new OpenWeatherMapHelper.CurrentWeatherCallback() {
                @Override
                public void onSuccess(CurrentWeather currentWeather) {
                    DateFormat df = DateFormat.getDateTimeInstance();
                    cityField.setText(currentWeather.getName());
                    updatedField.setText(df.format(new Date(currentWeather.getDt()*1000)));
                    sunrise = currentWeather.getSys().getSunrise()* 1000;
                    sunset = currentWeather.getSys().getSunset()* 1000;
                    weatherIcon.setText(Html.fromHtml(setWeatherIcon(currentWeather.getWeatherArray().get(0).getId().intValue(),
                            sunrise, sunset)));
                    detailsField.setText(currentWeather.getWeatherArray().get(0).getDescription());
                    temperatureField.setText(currentWeather.getMain().getTemp() + "°C");
                    humidityField.setText("Humidity : " + currentWeather.getMain().getHumidity() + "");
                    pressureField.setText("Pressure : " + currentWeather.getMain().getPressure() + "");
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Log.v(TAG, throwable.getMessage());
                }
            });
            helper.getThreeHourForecastByGeoCoordinates(mLatitude,mLongitude, new OpenWeatherMapHelper.ThreeHourForecastCallback() {
                @Override
                public void onSuccess(ThreeHourForecast threeHourForecast) {
                    DateFormat df = DateFormat.getDateTimeInstance();
                    String tanggal, cuaca;
                    for(int i=0;i<8;i++){
                        tanggal = df.format(new Date(threeHourForecast.getThreeHourWeatherArray().get(i).getDt()*1000));
                        cuaca = setWeatherIcon(threeHourForecast.getThreeHourWeatherArray().get(i).getWeatherArray().get(0).getId().intValue(),
                                sunrise, sunset);
                        listCuaca.add(new ListCuaca(tanggal,
                                Html.fromHtml(cuaca),
                                threeHourForecast.getThreeHourWeatherArray().get(i).getMain().getTemp()+"°C"));
                    }
                    mRecyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Log.v(TAG, throwable.getMessage());
                }
            });

        }
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

    public static String setWeatherIcon(int actualId, long sunrise, long sunset){
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = "&#xf00d;";
            } else {
                icon = "&#xf02e;";
            }
        } else {
            switch(id) {
                case 2 : icon = "&#xf01e;";
                    break;
                case 3 : icon = "&#xf01c;";
                    break;
                case 7 : icon = "&#xf014;";
                    break;
                case 8 : icon = "&#xf013;";
                    break;
                case 6 : icon = "&#xf01b;";
                    break;
                case 5 : icon = "&#xf019;";
                    break;
            }
        }
        return icon;
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
}