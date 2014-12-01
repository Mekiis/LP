package com.velorn;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.velorn.loaderJSon.LoaderJSonRunnable;
import com.velorn.loaderJSon.LoaderJson;
import com.velorn.loaderJSon.LoaderJsonParams;
import com.velorn.parser.PathParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IEM on 05/11/2014.
 */
public class ChosePath extends Activity {
    // WEB SERVICE GOOGLE MAP : Directions
    // Sert à tracer des parcours
    // http://javapapers.com/android/draw-path-on-google-maps-android-api/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new LoaderJson().execute(new LoaderJsonParams(
                "",
                new LoaderJSonRunnable() {
                    @Override
                    public void run() {
                        PathParser parser = new PathParser();
                        parser.parse(s);
                    }
                },
                getApplicationContext()));
    }

    public void constructPath(List<List> routes) {
        ArrayList points = null;
        PolylineOptions polyLineOptions = null;

        // traversing through routes
        for (int i = 0; i < routes.size(); i++) {
            points = new ArrayList();
            polyLineOptions = new PolylineOptions();
            List<HashMap> path = routes.get(i);

            for (int j = 0; j < path.size(); j++) {
                HashMap point = path.get(j);

                double lat = Double.parseDouble((String) point.get("lat"));
                double lng = Double.parseDouble((String) point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }

            polyLineOptions.addAll(points);
            polyLineOptions.width(2);
            polyLineOptions.color(Color.BLUE);
        }

        //googleMap.addPolyline(polyLineOptions);
    }
}
