package org.pindad.jemuran.Sensor.ModelSensor;

/**
 * Created by ASUS on 11/02/2018.
 */

public class ListSensor {
    boolean sensor_getar;
    boolean sensor_hujan;

    public ListSensor(){

    }

    public ListSensor(boolean sensor_getar, boolean sensor_hujan) {
        this.sensor_getar = sensor_getar;
        this.sensor_hujan = sensor_hujan;
    }

    public boolean getSensor_getar() {
        return sensor_getar;
    }

    public void setSensor_getar(boolean sensor_getar) {
        this.sensor_getar = sensor_getar;
    }

    public boolean getSensor_hujan() {
        return sensor_hujan;
    }

    public void setSensor_hujan(boolean sensor_hujan) {
        this.sensor_hujan = sensor_hujan;
    }
}
