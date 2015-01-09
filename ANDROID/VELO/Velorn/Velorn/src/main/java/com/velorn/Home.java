package com.velorn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends Activity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onChoseCity(View v) {
        intent = new Intent(this, ChooseCity.class);
        startActivity(intent);
    }

    public void onSeeBorn(View v) {
        intent = new Intent(this, ViewStations.class);
        startActivity(intent);
    }
}
