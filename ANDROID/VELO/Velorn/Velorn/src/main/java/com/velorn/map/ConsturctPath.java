package com.velorn.map;

import android.util.Log;

public class ConstructPath{
    private static Map googleMap = null;

    public enum EWayToTravel{
        Foot = "foot",
        Bike = "bike",
        Car = "car"
    }

    public void loadPath(String p1, String p2, EWayToTravel wayToTravel, Map googleMap){
        String url = "url.com/googleDirection.php?";
        url += "p1="+p1+"&";
        url += "p2="+p1+"&";
        url += "way="+wayToTravel.name();

        new LoaderJson().execute(new LoaderJsonParams(
                url,
                new LoaderJSonRunnable() {
                    @Override
                    public void run() {
                        PathParser parser = new PathParser();
                        constructPath(parser.parse(s))
                    }
                },
                getApplicationContext()));
    }

    public void constructPath(List<List> routes) {
        ArrayList points = null;
        PolylineOptions polyLineOptions = null;

        if(routes == null){
            Log.e(this.getClass().getName(), "No path to draw");
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
            Log.e(this.getClass().getName(), "No map find to draw path");
    }
}