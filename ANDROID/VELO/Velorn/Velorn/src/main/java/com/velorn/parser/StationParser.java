package com.velorn.parser;

import com.velorn.container.Station;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StationParser {

    // JSON Node names
    private final String TAG_NUMBER = "number";
    private final String TAG_CONTRACT_NAME = "contract_name";
    private final String TAG_NAME = "name";
    private final String TAG_ADDRESS = "address";
    private final String TAG_POS = "position";
    private final String TAG_POS_LAT = "lat";
    private final String TAG_POS_LONG = "lng";
    private final String TAG_BANKING = "banking";
    private final String TAG_BONUS = "bonus";
    private final String TAG_STATUS = "status";
    private final String TAG_BIKE_STANDS = "bike_stands";
    private final String TAG_AVAILABLE_BIKE_STANDS = "available_bike_stands";
    private final String TAG_AVAILABLE_BIKE = "available_bikes";
    private final String TAG_LAST_UPDATE = "last_update";

    // contacts JSONArray
    private JSONArray contacts = null;

    public ArrayList<Station> CreateStations(String json) {
        ArrayList<Station> stations = new ArrayList<Station>();

        try {
            // Getting Array of Contacts
            contacts = new JSONArray(json);

            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);

                Station station = new Station();

                // Storing each json item in variable
                station.number = c.getInt(TAG_NUMBER);
                station.contractName = c.getString(TAG_CONTRACT_NAME);
                station.name = c.getString(TAG_NAME);
                station.address = c.getString(TAG_ADDRESS);
                JSONObject position = c.getJSONObject(TAG_POS);
                station.pos.lat = position.getDouble(TAG_POS_LAT);
                station.pos.lng = position.getDouble(TAG_POS_LONG);
                station.banking = c.getBoolean(TAG_BANKING);
                station.bonus = c.getBoolean(TAG_BONUS);
                if (c.getString(TAG_STATUS).equalsIgnoreCase("OPEN")) {
                    station.status = Station.EStatus.OPEN;
                } else if (c.getString(TAG_STATUS).equalsIgnoreCase("CLOSE")) {
                    station.status = Station.EStatus.CLOSE;
                }
                station.bikeStands = c.getInt(TAG_BIKE_STANDS);
                station.availableBike = c.getInt(TAG_AVAILABLE_BIKE);
                station.availableBikeStands = c.getInt(TAG_AVAILABLE_BIKE_STANDS);
                //station.lastUpdate = c.getLong(TAG_LAST_UPDATE);

                stations.add(station);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return stations;
    }
}
