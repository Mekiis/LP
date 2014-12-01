package com.velorn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Accueil extends Activity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.accueil_menu_list:
                intent = new Intent(this, ViewStations.class);
                startActivity(intent);
                break;
            case R.id.accueil_menu_ville:
                intent = new Intent(this, ChooseCity.class);
                startActivity(intent);
                break;
        }
    }
}
