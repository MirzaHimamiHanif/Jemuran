package org.pindad.jemuran.sensor.modelsensor;

/**
 * Created by ASUS on 11/02/2018.
 */

public class ListSensor {
    boolean sensor_hujan;

    public ListSensor(){

    }

    public ListSensor(boolean sensor_hujan) {
        this.sensor_hujan = sensor_hujan;
    }

    public boolean isSensor_hujan() {
        return sensor_hujan;
    }

    public void setSensor_hujan(boolean sensor_hujan) {
        this.sensor_hujan = sensor_hujan;
    }
}
