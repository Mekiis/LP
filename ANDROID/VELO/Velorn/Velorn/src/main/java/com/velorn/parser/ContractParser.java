package com.velorn.parser;

import com.velorn.container.Contract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContractParser {

    // JSON Node names
    private final String TAG_NAME = "name";
    private final String TAG_COMMERCIAL_NAME = "commercial_name";
    private final String TAG_COUNTRY_CODE = "country_code";
    private final String TAG_CITIES = "cities";

    // contacts JSONArray
    private JSONArray contacts = null;

    public ArrayList<Contract> CreateContract(String json){
        ArrayList<Contract> contracts = new ArrayList<Contract>();

        try {
            // Getting Array of Contacts
            contacts = new JSONArray(json);

            // looping through All Contacts
            for(int i = 0; i < contacts.length(); i++){
                JSONObject c = contacts.getJSONObject(i);

                Contract contract = new Contract();

                // Storing each json item in variable
                contract.name = c.getString(TAG_NAME);
                contract.commercial_name = c.getString(TAG_COMMERCIAL_NAME);
                contract.country_code = c.getString(TAG_COUNTRY_CODE);
                JSONArray cities =  c.getJSONArray(TAG_CITIES);
                for(int j = 0; j < cities.length(); j++){
                    contract.cities.add(cities.getString(j));
                }


                contracts.add(contract);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contracts;
    }
}
