package com.velorn.container;

/**
 * Created by SJ on 15/10/2014.
 */
public class Station {
    public int number = -1;
    public String contractName = "";
    public String name = "";
    public String address = "";

    public class Position {
        public double lat = -1.0;
        public double lng = -1;
    }

    public Position pos = new Position();
    public boolean banking = false;
    public boolean bonus = false;

    public enum EStatus {
        OPEN,
        CLOSE;
    }

    public EStatus status = EStatus.OPEN;
    public int bikeStands = 0;
    public int availableBikeStands = 0;
    public int availableBike = 0;
    public long lastUpdate = 0;
}
