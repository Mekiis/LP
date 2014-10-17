package com.velorn;

import android.content.Intent;
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
import android.widget.Toast;

public class Accueil extends ActionBarActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.accueil_menu_list:
                intent = new Intent(this, ViewStations.class);
                startActivity(intent);
                break;
            case R.id.accueil_menu_ville:
                intent = new Intent(this, Ville.class);
                startActivity(intent);
                break;
        }
    }
}
