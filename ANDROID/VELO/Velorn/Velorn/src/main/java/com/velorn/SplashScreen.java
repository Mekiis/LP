package com.velorn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
    public static List<Bitmap> markerImage = new ArrayList<Bitmap>();
    public static final int NB_MARKER = 70;

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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), R.string.load_no_network, Toast.LENGTH_SHORT).show();
                            }
                        });
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

                        generateMarker();
                    }

                    @Override
                    public void errorNetwork() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), R.string.load_no_network, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }, getApplicationContext()));
    }

    public void generateMarker(){
        View marker = null;
        for(int i = 1; i < NB_MARKER; i++){
            marker = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_take, null);
            ((TextView) marker.findViewById(R.id.custom_marker_txt)).setText(Integer.toString(i));
            markerImage.add(createDrawableFromView(this, marker));
        }

        for(int i = 1; i < NB_MARKER; i++){
            marker = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_return, null);
            ((TextView) marker.findViewById(R.id.custom_marker_txt)).setText(Integer.toString(i));
            markerImage.add(createDrawableFromView(this, marker));
        }

        Intent i = new Intent(activity, Home.class);
        startActivity(i);
        finish();
    }

    // Convert a view to bitmap
    private Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }
}
