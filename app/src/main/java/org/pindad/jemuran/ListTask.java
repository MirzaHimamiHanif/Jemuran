package org.pindad.jemuran;

import android.os.Parcel;
import android.os.Parcelable;

import org.pindad.jemuran.cuaca.modelcuacaapi.modelforecast.ListHourly;
import org.pindad.jemuran.history.modelhistory.ListHistory;

import java.util.ArrayList;

public class ListTask implements Parcelable{
    private String time;
    private String weatherDesc;

    public ListTask() {

    }

    public ListTask(String time, String weatherDesc) {
        this.time = time;
        this.weatherDesc = weatherDesc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(String weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public ListTask(Parcel in){
        String[] data = new String[2];

        in.readStringArray(data);
        this.time = data[0];
        this.weatherDesc = data[1];
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.time,
                this.weatherDesc});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ListTask createFromParcel(Parcel in) {
            return new ListTask(in);
        }

        public ListTask[] newArray(int size) {
            return new ListTask[size];
        }
    };
}
