package com.velorn;

/**
 * Created by SJ on 15/10/2014.
 */
public class Station {
    int number = -1;
    String contractName = "";
    String name = "";
    String address = "";
    public class Position{
        public double lat = -1.0;
        public double lng = -1;
    }
    Position pos = new Position();
    boolean banking = false;
    boolean bonus = false;
    public enum EStatus{
        OPEN,
        CLOSE;
    }
    EStatus status = EStatus.OPEN;
    int bikeStands = 0;
    int availableBikeStands = 0;
    int availableBike = 0;
    long lastUpdate = 0;
}
