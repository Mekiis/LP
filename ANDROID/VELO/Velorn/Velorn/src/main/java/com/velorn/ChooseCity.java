package com.velorn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class ChooseCity extends ActionBarActivity {

    public static final String CITY_PREF = "VILLE_PREF";
    public static final String CITIES = "VILLE";

    private static AutoCompleteTextView UIcityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ville);

        UIcityName = ((AutoCompleteTextView) findViewById(R.id.ville_etxt_ville));

        String ville = getPref(CITIES, CITY_PREF, "");
        if (!ville.equalsIgnoreCase("")) {
            ville = ville.substring(0, 1).toUpperCase() + ville.substring(1).toLowerCase();
        }
        UIcityName.setText(ville);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SplashScreen.cities);
        UIcityName.setAdapter(adapter);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ville_btn_validate:
                String ville = UIcityName.getText().toString();
                setPref(CITIES, CITY_PREF, ville);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.ville_validate_msg), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(this, ViewStations.class);
                startActivity(i);
                finish();
                break;
        }
    }

    private String getPref(String file, String key, String defaulValue) {
        String s = key;
        SharedPreferences preferences = getSharedPreferences(file, 0);
        return preferences.getString(s, defaulValue);
    }

    private void setPref(String file, String key, String value) {
        SharedPreferences preferences = getSharedPreferences(file, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
