package com.velorn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChooseCity extends ActionBarActivity {

    public static final String CITY_PREF = "VILLE_PREF";
    public static final String CITIES = "VILLE";

    private static AutoCompleteTextView UIcityName;
    private static ImageView UIvalidateImage;
    private static TextView UIvalidateText;

    private static List<String> cities = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_city);

        UIcityName = ((AutoCompleteTextView) findViewById(R.id.ville_etxt_ville));
        UIvalidateImage = ((ImageView) findViewById(R.id.chose_city_iv_validate));
        UIvalidateText = ((TextView) findViewById(R.id.chose_city_tv_validate));

        cities.clear();
        for(String c : SplashScreen.cities){
            cities.add(c.toLowerCase());
        }

        String ville = getPref(CITIES, CITY_PREF, "");
        if (!ville.equalsIgnoreCase("")) {
            ville = ville.substring(0, 1).toUpperCase() + ville.substring(1).toLowerCase();
        }
        UIcityName.setText(ville);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SplashScreen.cities);
        UIcityName.setAdapter(adapter);

        displayValidate(UIcityName.getText().toString().toLowerCase());

        UIcityName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                displayValidate(s.toString());
            }
        });
    }

    private void displayValidate(String s){
        if(!cities.contains(s.toString().toLowerCase())){
            UIvalidateImage.getDrawable().setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.MULTIPLY);
            UIvalidateText.setTextColor(getResources().getColor(R.color.gray));
        } else {
            UIvalidateImage.getDrawable().setColorFilter(null);
            UIvalidateText.setTextColor(getResources().getColor(R.color.text_color));
        }
    }

    public void onValidate(View v) {
        String ville = UIcityName.getText().toString().toLowerCase();
        if(cities.contains(ville.toLowerCase())) {
            setPref(CITIES, CITY_PREF, ville);
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.ville_validate_msg), Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this, ViewStations.class);
            startActivity(i);
            finish();
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
