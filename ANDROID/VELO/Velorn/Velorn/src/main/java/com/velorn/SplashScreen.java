package com.velorn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.velorn.container.Contract;
import com.velorn.container.Stations;
import com.velorn.loaderJSon.LoaderJSonRunnable;
import com.velorn.loaderJSon.LoaderJson;
import com.velorn.loaderJSon.LoaderJsonParams;
import com.velorn.parser.ContractParser;
import com.velorn.parser.StationParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IEM on 05/11/2014.
 */
public class SplashScreen extends Activity {
    public static Stations stations = new Stations();
    public static List<String> cities = new ArrayList<String>();
    public static List<Contract> contracts = new ArrayList<Contract>();

    public Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Get the list of the stations
        new LoaderJson().execute(new LoaderJsonParams(
                "https://api.jcdecaux.com/vls/v1/stations?apiKey=416ddf2bf645df9eea6d7c874904e5626ae62ffd",
                new LoaderJSonRunnable() {
                    @Override
                    public void run() {
                        stations.forceUpdate(new StationParser().CreateStations(s));
                    }

                    @Override
                    public void errorNetwork() {
                        Toast.makeText(getApplicationContext(), R.string.load_no_network, Toast.LENGTH_SHORT);
                    }
                }, getApplicationContext()));


        // Get the list of the stations
        new LoaderJson().execute(new LoaderJsonParams(
                "https://api.jcdecaux.com/vls/v1/contracts?apiKey=416ddf2bf645df9eea6d7c874904e5626ae62ffd",
                new LoaderJSonRunnable() {
                    @Override
                    public void run() {
                        SplashScreen.contracts = new ContractParser().CreateContract(s);
                        for (int i = 0; i < SplashScreen.contracts.size(); i++) {
                            for (int j = 0; j < SplashScreen.contracts.get(i).cities.size(); j++) {
                                if (!SplashScreen.cities.contains(SplashScreen.contracts.get(i).cities.get(j))) {
                                    SplashScreen.cities.add(SplashScreen.contracts.get(i).cities.get(j));
                                }
                            }
                        }

                        Intent i = new Intent(activity, Accueil.class);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void errorNetwork() {
                        Toast.makeText(getApplicationContext(), R.string.load_no_network, Toast.LENGTH_SHORT);
                    }
                }, getApplicationContext()));
    }
}
