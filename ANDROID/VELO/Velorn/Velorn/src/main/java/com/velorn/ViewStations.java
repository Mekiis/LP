package com.velorn;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.velorn.container.Stations;
import com.velorn.loaderJSon.LoaderJSonRunnable;
import com.velorn.loaderJSon.LoaderJson;
import com.velorn.loaderJSon.LoaderJsonParams;
import com.velorn.map.ConstructPath;
import com.velorn.map.CustomClusterRenderer;
import com.velorn.map.CustomMarkerItem;
import com.velorn.parser.StationParser;

import java.util.List;

public class ViewStations extends ActionBarActivity implements ConstructPath.EventListener {
    private static final String SINGLE_LOCATION_UPDATE_ACTION = "SINGLE_LOCATION_UPDATE_ACTION";
    public static final int CHOSE_PATH_REQUEST = 0;
    public static final int CHOSE_PATH_OK = 1;
    public static final String CHOSE_PATH_ORIGIN = "CHOSE_PATH_ORIGIN";
    public static final String CHOSE_PATH_DESTINATION = "CHOSE_PATH_DESTINATION";
    public static final String CHOSE_PATH_WAY_TO_TRAVEL = "CHOSE_PATH_WAY_TO_TRAVEL";
    // TODO : UI::Put colors on the selection option items (Purple /Light blue)

    // TODO : Do a button on marker to do the selection path
    // http://stackoverflow.com/questions/14123243/google-maps-android-api-v2-interactive-infowindow-like-in-original-android-go/15040761#15040761

    // UI elements
    private static ProgressBar UIloaderBar = null;
    private static TextView UImsgError = null;

    // Gmap
    private static GoogleMap UImap; // Might be null if Google Play services APK is not available.
    private static ClusterManager cluster;
    private static Marker lastOpen = null;

    // GPS
    private static LocationManager locationManager;
    private static LocationListener locationListener;

    public Context context = this;
    private Object thisO = ((Object) this);
    private Activity thisA = this;

    // Research options
    public enum ESearchState {
        TAKE,
        RETURN;
    }

    // Logics elements
    private static ESearchState stateSearch = ESearchState.TAKE;
    private static String cityName = "";

    // Location
    private static PendingIntent singleUpatePI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Layout Management
        setContentView(R.layout.activity_view_stations_map);

        // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);

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

        if(cityName != null && SplashScreen.cities.contains(cityName))
            focusOnLocation(getLocationFromAddress(cityName));
    }

    private void init() {
        // Do a null check to confirm that we have not already instantiated the map.

        UImap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        if (UImap != null) {
            cluster = new ClusterManager<CustomMarkerItem>(getApplicationContext(), UImap);
            cluster.setRenderer(new CustomClusterRenderer(getApplicationContext(), UImap, cluster));
            UImap.setOnCameraChangeListener(cluster);
            UImap.setOnMarkerClickListener(cluster);
        } else {
            Log.e(thisO.getClass().getName(), "Error - No map detected");
        }

        // UI Management
        UIloaderBar = (ProgressBar) findViewById(R.id.view_stations_loadBar);
        UImsgError = (TextView) findViewById(R.id.view_stations_txt_error);

        displayNothing();

        cityName = getPref(ChooseCity.CITIES, ChooseCity.CITY_PREF, "");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();

        UImap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker) {
                // Check if there is an open info window
                if (lastOpen != null) {
                    if (lastOpen.equals(marker)) {
                        lastOpen = null;
                        onChosePath(marker.getTitle());
                        return true;
                    } else {
                        // Close the info window
                        lastOpen.hideInfoWindow();
                    }
                }

                // Open the info window for the marker
                marker.showInfoWindow();
                // Re-assign the last opened such that we can close it later
                lastOpen = marker;

                // Event was handled by our code do not launch default behaviour.
                return true;
            }
        });
    }

    private String getPref(String file, String key, String defaulValue) {
        String s = key;
        SharedPreferences preferences = getSharedPreferences(file, 0);
        return preferences.getString(s, defaulValue);
    }

    private void displayStations(Stations stations) {
        if (stations == null || stations.getListStations().size() == 0)
            return;

        displayMarker(stations);

        UIloaderBar.setVisibility(View.GONE);
        UImsgError.setVisibility(View.GONE);
    }

    private void displayNothing(){
        UIloaderBar.setVisibility(View.GONE);
        UImsgError.setVisibility(View.GONE);
    }

    private void displayErrorMsg() {
        UIloaderBar.setVisibility(View.GONE);
        UImsgError.setVisibility(View.VISIBLE);
    }

    private void displayLoadingBar() {
        UIloaderBar.setVisibility(View.VISIBLE);
        UImsgError.setVisibility(View.GONE);
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
                    cluster.addItem(new CustomMarkerItem(stations.getListStations().get(i),stateSearch,getApplicationContext() ));
                } else if (stateSearch == ESearchState.RETURN && stations.getListStations().get(i).availableBikeStands > 0) {
                    cluster.addItem(new CustomMarkerItem(stations.getListStations().get(i),stateSearch,getApplicationContext() ));
                }
            }
        }
        cluster.cluster();
    }

    public void onChosePath(String destination){
        Intent intent = new Intent(this, ViewStations.class);
        intent.putExtra(CHOSE_PATH_DESTINATION, destination);
        startActivityForResult(intent, CHOSE_PATH_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHOSE_PATH_REQUEST) {
            if (resultCode == CHOSE_PATH_OK) {
                displayLoadingBar();
                String originAdress = data.getStringExtra(CHOSE_PATH_ORIGIN);
                LatLng origin = new LatLng(0, 0);
                if(originAdress.equalsIgnoreCase(getResources().getString(R.string.chose_path_user_position))){
                    origin = new LatLng(0, 0);
                } else {
                    origin = getLocationFromAddress(originAdress);
                }
                String destinationAdress = data.getStringExtra(CHOSE_PATH_DESTINATION);
                LatLng destination = getLocationFromAddress(destinationAdress);

                String wayToTravel = data.getStringExtra(CHOSE_PATH_WAY_TO_TRAVEL);

                ConstructPath c = new ConstructPath();
                c.addEventListener(this);
                c.loadPath(origin.latitude + "," + origin.longitude,
                        destination.latitude + "," + destination.longitude,
                        ConstructPath.getEnum(wayToTravel),
                        UImap,
                        getApplicationContext());
            }
        }
    }

    @Override
    public void loadFinish() {
        displayNothing();
        requestLocation();
    }

    /**
     * On click function to change the filter of display of the boundary marker on the view
     *
     * @param v The view clicked
     */
    public void onSearchChange(View v) {
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

    public void onRefresh(View v) {
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
    }

    public void onChoseCity(View v) {
        Intent intent = new Intent(this, ChooseCity.class);
        startActivity(intent);
        finish();
    }

    public void onLocalisation(View v){
        requestLocation();
    }

    private void requestLocation(){
        registerReceiver(singleUpdateReceiver, new IntentFilter(SINGLE_LOCATION_UPDATE_ACTION));
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_LOW);
        Intent updateIntent = new Intent(SINGLE_LOCATION_UPDATE_ACTION);
        singleUpatePI = PendingIntent.getBroadcast(this, 0, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        locationManager.requestSingleUpdate(criteria, singleUpatePI);
    }

    protected BroadcastReceiver singleUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(this.getClass().getName(),"onReceive");
            //unregister the receiver so that the application does not keep listening the broadcast even after the broadcast is received.
            context.unregisterReceiver(singleUpdateReceiver);
            // get the location from the intent send in broadcast using the key - this step is very very important
            String key = LocationManager.KEY_LOCATION_CHANGED;
            Location location = (Location)intent.getExtras().get(key);

            // Call the function required
            if (location != null) {
                focusOnLocation(new LatLng(location.getLatitude(), location.getLongitude()));
            }

            // finally remove the updates for the pending intent
            locationManager.removeUpdates(singleUpatePI);
        }
    };

    private void focusOnLocation(LatLng location) {

        CameraPosition camPos = new CameraPosition.Builder()
                .target(location)
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
