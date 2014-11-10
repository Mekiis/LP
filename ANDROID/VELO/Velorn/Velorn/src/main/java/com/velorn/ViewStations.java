package com.velorn;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

    // UI elements
    private ProgressBar loaderBar = null;
    private TextView msgError = null;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    // Research options
    private enum ESearchState{
        TAKE,
        RETURN;
    }
    private ESearchState stateSearch = ESearchState.TAKE;
    private String city = "";

    private ClusterManager cluster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Layout Management
        setContentView(R.layout.activity_view_stations_map);

        initMap();
        initViewsStation();
    }


    @Override
    protected void onStart() {
        super.onStart();

        city = ((EditText)findViewById(R.id.view_stations_tv_name_city)).getText().toString();

        displayMarker(SplashScreen.stations);
        Log.d(this.getClass().getName(), "Nb stations : " + SplashScreen.stations.getListStations().size());

        // TODO : Verify that the update delay is correct (5 minutes)
        if(SplashScreen.stations.startUpdate()){

            Log.d(this.getClass().getName(), "Updating list of stations");
            // Get the list of the stations
            new LoaderJson().execute(new LoaderJsonParams(
                    "https://api.jcdecaux.com/vls/v1/stations?apiKey=416ddf2bf645df9eea6d7c874904e5626ae62ffd",
                    new LoaderJSonRunnable() {
                        @Override
                        public void run() {
                            SplashScreen.stations.update(new StationParser().CreateStations(s));
                            displayStations(SplashScreen.stations);
                            Log.d(this.getClass().getName(), "Refresh of the display of the stations =====");
                            Log.d(this.getClass().getName(), "Nb stations : " + SplashScreen.stations.getListStations().size());
                            Log.d(this.getClass().getName(), "=================");
                        }

                        @Override
                        public void errorNetwork() {
                            Toast.makeText(getApplicationContext(), R.string.load_no_network, Toast.LENGTH_SHORT);
                        }
                    }, getApplicationContext()));
        }

    }

    private void initViewsStation(){
        // UI Management
        loaderBar = (ProgressBar) findViewById(R.id.view_stations_loadBar);
        msgError = (TextView) findViewById(R.id.view_stations_txt_error);
        loaderBar.setVisibility(View.GONE);
        msgError.setVisibility(View.GONE);

        city = getPref(ChooseCity.CITIES, ChooseCity.CITY_PREF, "");
        if(!city.equalsIgnoreCase(""))
            displayCity(city);
        else
            displayCity(getResources().getString(R.string.view_stations_tv_city_name_none));

        ((EditText)findViewById(R.id.view_stations_tv_name_city)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.d(this.getClass().getName(), "Display the new name of city and display all the stations");
                    city = ((EditText) v).getText().toString();
                    displayCity(city);
                    displayMarker(SplashScreen.stations);
                    return true;
                }
                return false;
            }
        });
        // TODO : Find how to get the down clavier event on the edit text
    }

    private void displayCity(String city){
        if(!city.equalsIgnoreCase("")){
            ((EditText)findViewById(R.id.view_stations_tv_name_city)).setText(city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase());
            Log.d(this.getClass().getName(), "City : "+city);
            focusOnLocation(city.toLowerCase());
        }
    }

    private void focusOnLocation(String city){

        CameraPosition camPos = new CameraPosition.Builder()
                .target(getLocationFromAddress(city))
                .zoom(12)
                .build();

        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);

        Log.d(this.getClass().getName(), "Position : "+camPos);
        Log.d(this.getClass().getName(), "Cam position : "+camUpd3);

        mMap.animateCamera(camUpd3);
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

            p1 = new LatLng(location.getLatitude(),location.getLongitude());

        } catch (Exception e){
            Log.e(this.getClass().getName(), "Error - While convert");
        }

        Log.d(this.getClass().getName(), "Lat : "+p1.latitude+" / Lng : "+p1.longitude);

        return p1;
    }

    String getPref(String file, String key, String defaulValue) {
        String s = key;
        SharedPreferences preferences = getSharedPreferences(file, 0);
        return preferences.getString(s, defaulValue);
    }

    void displayStations(Stations stations){
        if(stations == null || stations.getListStations().size() == 0)
            return;

        displayMarker(stations);

        loaderBar.setVisibility(View.GONE);
        msgError.setVisibility(View.GONE);
    }

    void displayErrorMsg(){
        loaderBar.setVisibility(View.GONE);
        msgError.setVisibility(View.VISIBLE);
    }

    private void initMap() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
        }

        if (mMap != null) {
            cluster = new ClusterManager<MarkerItem>(getApplicationContext(), mMap);
            cluster.setRenderer(new CustomClusterRenderer(getApplicationContext(), mMap, cluster));
            mMap.setOnCameraChangeListener(cluster);
            mMap.setOnMarkerClickListener(cluster);
        }
    }

    private void displayMarker(Stations stations) {
        if (mMap != null && cluster != null) {
            // Add remove Marker
            cluster.clearItems();
        } else
            return;

        if(stations == null)
            return;

        for(int i = 0; i < stations.getListStations().size(); i++){
            if(stations.getListStations().get(i).contractName.equalsIgnoreCase(city) || city.equalsIgnoreCase("")){
                if(stateSearch == ESearchState.TAKE && stations.getListStations().get(i).availableBike > 0){
                        cluster.addItem(new MarkerItem( stations.getListStations().get(i).pos.lat, stations.getListStations().get(i).pos.lng,
                                                        stations.getListStations().get(i).availableBike,
                                                        stations.getListStations().get(i).availableBikeStands,
                                                        stateSearch));
                } else if(stateSearch == ESearchState.RETURN && stations.getListStations().get(i).availableBikeStands > 0){
                        cluster.addItem(new MarkerItem( stations.getListStations().get(i).pos.lat, stations.getListStations().get(i).pos.lng,
                                                        stations.getListStations().get(i).availableBike,
                                                        stations.getListStations().get(i).availableBikeStands,
                                                        stateSearch));
                }
            }
        }
        cluster.cluster();

    }

    private class MarkerItem implements ClusterItem {
        private LatLng position = null;
        private int nbBikeFree = 0;
        private int nbPlaceFree = 0;
        private ESearchState state = ESearchState.TAKE;

        public MarkerItem(double lat, double lng, int nbBikeFree, int nbPlaceFree, ESearchState state){
            this.position = new LatLng(lat, lng);
            this.nbBikeFree = nbBikeFree;
            this.nbPlaceFree = nbPlaceFree;
            this.state = state;
        }

        @Override
        public LatLng getPosition() {
            return position;
        }

        public String getTag(){
            if(state == ESearchState.TAKE){
                return ""+nbBikeFree;
            } else {
                return ""+nbPlaceFree;
            }
        }

        public float getColor(){
            float[] HSV = new float[3];
            if(state == ESearchState.TAKE){
                new Color().colorToHSV(getResources().getColor(R.color.views_stations_bike_avaible), HSV);
            } else {
                new Color().colorToHSV(getResources().getColor(R.color.views_stations_place_avaible), HSV);
            }
            return HSV[0];
        }


    }

    private class CustomClusterRenderer extends DefaultClusterRenderer<MarkerItem> {

        public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected void onBeforeClusterItemRendered(MarkerItem item, MarkerOptions markerOptions) {
            super.onBeforeClusterItemRendered(item, markerOptions);
            markerOptions.title(item.getTag());
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(item.getColor()));
        }
    }

    public void searchChange(View v){
        switch(v.getId()){
            case R.id.view_stations_rb_take:
                if(stateSearch != ESearchState.TAKE){
                    stateSearch = ESearchState.TAKE;
                    displayMarker(SplashScreen.stations);
                }
                break;
            case R.id.view_stations_rb_return:
                if(stateSearch != ESearchState.RETURN){
                    stateSearch = ESearchState.RETURN;
                    displayMarker(SplashScreen.stations);
                }
                break;
        }
    }
}
