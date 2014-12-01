package com.velorn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.*;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.velorn.container.Stations;
import com.velorn.loaderJSon.LoaderJSonRunnable;
import com.velorn.loaderJSon.LoaderJson;
import com.velorn.loaderJSon.LoaderJsonParams;
import com.velorn.parser.StationParser;

import java.util.List;

public class ViewStations extends ActionBarActivity {
    // TODO : UI::Put colors on the selection option items (Purple /Light blue)

    // TODO : Do a button on marker to do the selection path
    // http://stackoverflow.com/questions/14123243/google-maps-android-api-v2-interactive-infowindow-like-in-original-android-go/15040761#15040761

    // UI elements
    private static ProgressBar UIloaderBar = null;
    private static TextView UImsgError = null;
    // Gmap
    private static GoogleMap UImap; // Might be null if Google Play services APK is not available.
    private static ClusterManager cluster;

    // GPS
    private static LocationManager locationManager;
    private static LocationListener locationListener;

    public Context context = this;
    private Object thisO = ((Object) this);

    // Research options
    private enum ESearchState {
        TAKE,
        RETURN;
    }

    // Logics elements
    private static ESearchState stateSearch = ESearchState.TAKE;
    private static String cityName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Layout Management
        setContentView(R.layout.activity_view_stations_map);

        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);

        init();

        displayStations(SplashScreen.stations);
        Log.d(thisO.getClass().getName(), "Nb stations : " + SplashScreen.stations.getListStations().size());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (SplashScreen.stations.startUpdate()) {
            Log.d(thisO.getClass().getName(), "Updating list of stations");
            // Get the list of the stations
            new LoaderJson().execute(new LoaderJsonParams(
                    "https://api.jcdecaux.com/vls/v1/stations?apiKey=416ddf2bf645df9eea6d7c874904e5626ae62ffd",
                    new LoaderJSonRunnable() {
                        @Override
                        public void run() {
                            SplashScreen.stations.update(new StationParser().CreateStations(s));
                            displayStations(SplashScreen.stations);
                            Log.d(thisO.getClass().getName(), "Refresh of the display of the stations =====");
                            Log.d(thisO.getClass().getName(), "Nb stations : " + SplashScreen.stations.getListStations().size());
                            Log.d(thisO.getClass().getName(), "=================");
                        }

                        @Override
                        public void errorNetwork() {
                            Toast.makeText(getApplicationContext(), R.string.load_no_network, Toast.LENGTH_SHORT);
                        }
                    }, getApplicationContext()));
        } else {
            Log.d(thisO.getClass().getName(), "Don't update list of stations");
        }


    }

    private void init() {
        // Do a null check to confirm that we have not already instantiated the map.

        UImap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        if (UImap != null) {
            cluster = new ClusterManager<MarkerItem>(getApplicationContext(), UImap);
            cluster.setRenderer(new CustomClusterRenderer(getApplicationContext(), UImap, cluster));
            UImap.setOnCameraChangeListener(cluster);
            UImap.setOnMarkerClickListener(cluster);
        } else {
            Log.e(thisO.getClass().getName(), "Error - No map detected");
        }

        // UI Management
        UIloaderBar = (ProgressBar) findViewById(R.id.view_stations_loadBar);
        UImsgError = (TextView) findViewById(R.id.view_stations_txt_error);
        UIloaderBar.setVisibility(View.GONE);
        UImsgError.setVisibility(View.GONE);

        cityName = getPref(ChooseCity.CITIES, ChooseCity.CITY_PREF, "");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();
    }

    private void focusOnLocation(String city) {

        CameraPosition camPos = new CameraPosition.Builder()
                .target(getLocationFromAddress(city))
                .zoom(12)
                .build();

        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);

        Log.d(thisO.getClass().getName(), "Position : " + camPos);
        Log.d(thisO.getClass().getName(), "Cam position : " + camUpd3);

        UImap.animateCamera(camUpd3);
    }

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(getBaseContext());
        List<Address> address = null;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (Exception e) {
            Log.e(thisO.getClass().getName(), "Error - While convert");
        }

        Log.d(thisO.getClass().getName(), "Lat : " + p1.latitude + " / Lng : " + p1.longitude);

        return p1;
    }

    String getPref(String file, String key, String defaulValue) {
        String s = key;
        SharedPreferences preferences = getSharedPreferences(file, 0);
        return preferences.getString(s, defaulValue);
    }

    void displayStations(Stations stations) {
        if (stations == null || stations.getListStations().size() == 0)
            return;

        displayMarker(stations);

        UIloaderBar.setVisibility(View.GONE);
        UImsgError.setVisibility(View.GONE);
    }

    void displayErrorMsg() {
        UIloaderBar.setVisibility(View.GONE);
        UImsgError.setVisibility(View.VISIBLE);
    }

    private void displayMarker(Stations stations) {
        if (UImap != null && cluster != null) {
            // Add remove Marker
            cluster.clearItems();
        } else
            return;

        if (stations == null)
            return;

        for (int i = 0; i < stations.getListStations().size(); i++) {
            if (stations.getListStations().get(i).contractName.equalsIgnoreCase(cityName) || cityName.equalsIgnoreCase("")) {
                if (stateSearch == ESearchState.TAKE && stations.getListStations().get(i).availableBike > 0) {
                    cluster.addItem(new MarkerItem(stations.getListStations().get(i).pos.lat, stations.getListStations().get(i).pos.lng,
                            stations.getListStations().get(i).availableBike,
                            stations.getListStations().get(i).availableBikeStands,
                            stateSearch));
                } else if (stateSearch == ESearchState.RETURN && stations.getListStations().get(i).availableBikeStands > 0) {
                    cluster.addItem(new MarkerItem(stations.getListStations().get(i).pos.lat, stations.getListStations().get(i).pos.lng,
                            stations.getListStations().get(i).availableBike,
                            stations.getListStations().get(i).availableBikeStands,
                            stateSearch));
                }
            }
        }
        cluster.cluster();

    }

    /**
     * On click function to change the filter of display of the boundary marker on the view
     *
     * @param v The view clicked
     */
    public void searchChange(View v) {
        switch (v.getId()) {
            case R.id.view_stations_rb_take:
                if (stateSearch != ESearchState.TAKE) {
                    stateSearch = ESearchState.TAKE;
                    displayMarker(SplashScreen.stations);
                }
                break;
            case R.id.view_stations_rb_return:
                if (stateSearch != ESearchState.RETURN) {
                    stateSearch = ESearchState.RETURN;
                    displayMarker(SplashScreen.stations);
                }
                break;
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_stations_ib_city:
                Intent intent = new Intent(this, ChooseCity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.view_stations_ib_refresh:
                new LoaderJson().execute(new LoaderJsonParams(
                        "https://api.jcdecaux.com/vls/v1/stations?apiKey=416ddf2bf645df9eea6d7c874904e5626ae62ffd",
                        new LoaderJSonRunnable() {
                            @Override
                            public void run() {
                                SplashScreen.stations.update(new StationParser().CreateStations(s));
                                displayStations(SplashScreen.stations);
                                Log.d(thisO.getClass().getName(), "Refresh of the display of the stations =====");
                                Log.d(thisO.getClass().getName(), "Nb stations : " + SplashScreen.stations.getListStations().size());
                                Log.d(thisO.getClass().getName(), "=================");
                            }

                            @Override
                            public void errorNetwork() {
                                Toast.makeText(getApplicationContext(), R.string.load_no_network, Toast.LENGTH_SHORT);
                            }
                        }, getApplicationContext()));
            case R.id.view_stations_ib_localisation:
                break;
        }
    }

    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {
            if (loc != null) {
                Toast.makeText(getBaseContext(), "Localisation actuelle :n Lat: " + loc.getLatitude() + "  Lng: " + loc.getLongitude(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override

        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }
}
