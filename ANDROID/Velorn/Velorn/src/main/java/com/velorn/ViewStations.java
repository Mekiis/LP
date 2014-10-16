package com.velorn;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewStations extends ActionBarActivity {
    public ProgressBar loaderBar = null;
    public TextView msgError = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);

        loaderBar = (ProgressBar) findViewById(R.id.view_stations_loadBar);
        msgError = (TextView) findViewById(R.id.view_stations_txt_error);
        loaderBar.setVisibility(View.VISIBLE);
        msgError.setVisibility(View.GONE);

        displayStations(null);
    }

    public void displayStations(ArrayList<Station> listStations){
        if(listStations == null || listStations.size() == 0)
            return;

        loaderBar.setVisibility(View.GONE);
        msgError.setVisibility(View.GONE);
    }

    public void displayErrorMsg(){
        loaderBar.setVisibility(View.GONE);
        msgError.setVisibility(View.VISIBLE);
    }

    private class LoaderStations extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
