package org.pindad.jemuran.home.sistem.modelsistem;

public class ListSistem {
    private boolean sistem_anti_maling;
    private boolean sistem_jemuran;

    public ListSistem() {
    }

    public ListSistem(boolean sistem_anti_maling, boolean sistem_jemuran) {
        this.sistem_anti_maling = sistem_anti_maling;
        this.sistem_jemuran = sistem_jemuran;
    }

    public boolean getSistem_anti_maling() {
        return sistem_anti_maling;
    }

    public void setSistem_anti_maling(boolean sistem_anti_maling) {
        this.sistem_anti_maling = sistem_anti_maling;
    }

    public boolean getSistem_jemuran() {
        return sistem_jemuran;
    }

    public void setSistem_jemuran(boolean sistem_jemuran) {
        this.sistem_jemuran = sistem_jemuran;
    }
}
