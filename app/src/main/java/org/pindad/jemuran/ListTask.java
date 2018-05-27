package org.pindad.jemuran;

public class ListTask {
    private boolean sistemJemuran;
    private boolean statusAtap;
    private String weatherDesc;

    public ListTask() {
    }

    public ListTask(boolean sistemJemuran, boolean statusAtap, String weatherDesc) {
        this.sistemJemuran = sistemJemuran;
        this.statusAtap = statusAtap;
        this.weatherDesc = weatherDesc;
    }

    public boolean isSistemJemuran() {
        return sistemJemuran;
    }

    public void setSistemJemuran(boolean sistemJemuran) {
        this.sistemJemuran = sistemJemuran;
    }

    public boolean isStatusAtap() {
        return statusAtap;
    }

    public void setStatusAtap(boolean statusAtap) {
        this.statusAtap = statusAtap;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(String weatherDesc) {
        this.weatherDesc = weatherDesc;
    }
}
