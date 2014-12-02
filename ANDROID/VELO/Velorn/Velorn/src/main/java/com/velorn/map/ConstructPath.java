package com.velorn.map;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.velorn.R;
import com.velorn.loaderJSon.LoaderJSonRunnable;
import com.velorn.loaderJSon.LoaderJson;
import com.velorn.loaderJSon.LoaderJsonParams;
import com.velorn.parser.PathParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstructPath{
    private static GoogleMap googleMap = null;
    private EventListener listener = null;

    public enum EWayToTravel{
        driving,
        walking,
        bicycling
    }

    public interface EventListener{
        public void loadFinish();
    }

    public void addEventListener(EventListener e){
        listener = e;
    }

    public void loadPath(String p1, String p2, EWayToTravel wayToTravel, GoogleMap gMap, Context ctx){
        String url = "url.com/googleDirection.php?";
        url += "origin="+p1+"&";
        url += "destination="+p1+"&";
        url += "mode="+wayToTravel.name();
        url += "key="+ctx.getResources().getString(R.string.google_maps_key);

        googleMap = gMap;

        new LoaderJson().execute(new LoaderJsonParams(
                url,
                new LoaderJSonRunnable() {
                    @Override
                    public void run() {
                        PathParser parser = new PathParser();
                        constructPath(parser.parse(s));
                        if(listener != null){
                            listener.loadFinish();
                        }
                    }
                },
                ctx));
    }

    public static EWayToTravel getEnum(String str){
        EWayToTravel way = EWayToTravel.walking;
        if(str.equalsIgnoreCase(EWayToTravel.bicycling.name())) {
            way = EWayToTravel.bicycling;
        } else if(str.equalsIgnoreCase(EWayToTravel.walking.name())) {
            way = EWayToTravel.walking;
        } else if(str.equalsIgnoreCase(EWayToTravel.driving.name())) {
            way = EWayToTravel.driving;
        }

        return way;
    }

    public void constructPath(List<List> routes) {
        ArrayList points = null;
        PolylineOptions polyLineOptions = null;

        if(routes == null){
            Log.e("ConstructPath", "No path to draw");
            return;
        }

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

        if(googleMap != null)
            googleMap.addPolyline(polyLineOptions);
        else
            Log.e("ConstructPath", "No map find to draw path");
    }
}