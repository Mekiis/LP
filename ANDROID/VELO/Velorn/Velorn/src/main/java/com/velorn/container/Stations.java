package com.velorn.container;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IEM on 10/11/2014.
 */
public class Stations {
    private static List<Station> stations = new ArrayList<Station>();
    private static long lastUpdate = -1;
    private static boolean isUpdating = false;

    private static final int DELAY_UPDATE = 5 * 1000 * 60; // 5 minutes

    public Stations(ArrayList<Station> stations) {
        this.stations = stations;
        this.lastUpdate = System.currentTimeMillis();
    }

    public Stations() {
        this.stations = new ArrayList<Station>();
        this.lastUpdate = -1;
    }

    public boolean startUpdate() {
        boolean canUpdate = false;

        if (!this.isUpdating && needUpdate()) {
            this.isUpdating = true;
            canUpdate = true;
        }

        return canUpdate;
    }

    public boolean update(ArrayList<Station> stations) {
        boolean isUpdate = false;

        if (this.isUpdating) {
            if (needUpdate()) {
                this.stations = stations;
                this.lastUpdate = System.currentTimeMillis();
                isUpdate = true;
            }
            this.isUpdating = false;
        }


        return isUpdate;
    }

    public void forceUpdate(ArrayList<Station> stations) {
        if (this.isUpdating) {
            this.isUpdating = true;

            this.stations = stations;
            this.lastUpdate = System.currentTimeMillis();

            this.isUpdating = false;
        }
    }

    public boolean needUpdate() {
        boolean needUpdate = false;

        if (System.currentTimeMillis() - this.lastUpdate > DELAY_UPDATE) {
            needUpdate = true;
        }

        return needUpdate;
    }

    public List<Station> getListStations() {
        return this.stations;
    }
}
