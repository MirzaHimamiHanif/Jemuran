package org.pindad.jemuran.Sensor.ModelSensor;

/**
 * Created by ASUS on 11/02/2018.
 */

public class ListSensor {
    long sensor_getar;
    long sensor_hujan;

    public ListSensor(){

    }

    public ListSensor(long sensor_getar, long sensor_hujan) {
        this.sensor_getar = sensor_getar;
        this.sensor_hujan = sensor_hujan;
    }

    public long getSensor_getar() {
        return sensor_getar;
    }

    public void setSensor_getar(long sensor_getar) {
        this.sensor_getar = sensor_getar;
    }

    public long getSensor_hujan() {
        return sensor_hujan;
    }

    public void setSensor_hujan(long sensor_hujan) {
        this.sensor_hujan = sensor_hujan;
    }
}
