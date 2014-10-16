package com.velorn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;
import android.widget.Toast;

public class Ville extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ville);

        String ville = getPref("VILLE", "VILLE_PREF", "");
        if(!ville.equalsIgnoreCase("")){
            ville = ville.substring(0, 1).toUpperCase() + ville.substring(1).toLowerCase();
        }
        ((EditText) findViewById(R.id.ville_etxt_ville)).setText(ville);

    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.ville_btn_validate:
                String ville = ((EditText) findViewById(R.id.ville_etxt_ville)).getText().toString();
                setPref("VILLE", "VILLE_PREF", ville);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.ville_validate_msg), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    protected String getPref(String file, String key, String defaulValue) {
        String s = key;
        SharedPreferences preferences = getSharedPreferences(file, 0);
        return preferences.getString(s, defaulValue);
    }

    protected void setPref(String file, String key, String value) {
        SharedPreferences preferences = getSharedPreferences(file, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
