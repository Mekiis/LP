package com.velorn;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.velorn.container.Station;
import com.velorn.loaderJSon.LoaderJSonRunnable;
import com.velorn.loaderJSon.LoaderJson;
import com.velorn.loaderJSon.LoaderJsonParams;
import com.velorn.parser.StationParser;

import java.util.ArrayList;
import java.util.List;

public class ViewStations extends ActionBarActivity {
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

        initViewsStation();
        initMap();
    }


    @Override
    protected void onStart() {
        super.onStart();

        displayMarker(SplashScreen.stations);

        // Get the list of the stations
        new LoaderJson().execute(new LoaderJsonParams(
        "https://api.jcdecaux.com/vls/v1/stations?apiKey=416ddf2bf645df9eea6d7c874904e5626ae62ffd",
        new LoaderJSonRunnable() {
            @Override
            public void run() {
                SplashScreen.stations = new StationParser().CreateStations(s);
                displayStations(SplashScreen.stations);
            }

            @Override
            public void errorNetwork() {
                Toast.makeText(getApplicationContext(), R.string.load_no_network, Toast.LENGTH_SHORT);
            }
        }, getApplicationContext()));
    }

    private void initViewsStation(){
        // UI Management
        loaderBar = (ProgressBar) findViewById(R.id.view_stations_loadBar);
        msgError = (TextView) findViewById(R.id.view_stations_txt_error);
        loaderBar.setVisibility(View.GONE);
        msgError.setVisibility(View.GONE);

        city = getPref(ChooseCity.CITIES, ChooseCity.CITY_PREF, "");
        if(!city.equalsIgnoreCase(""))
            ((TextView) findViewById(R.id.view_stations_tv_name_city)).setText(city);
        else
            ((TextView) findViewById(R.id.view_stations_tv_name_city)).setText(getResources().getString(R.string.view_stations_tv_city_name_none));

        ((EditText)findViewById(R.id.view_stations_tv_name_city)).setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            /* When focus is lost check that the text field
            * has valid values.
            */
                if (!hasFocus) {
                    city = ((EditText) v).getText().toString();
                    displayCity(city);
                    displayMarker(SplashScreen.stations);
                }
            }
        });
    }

    private void displayCity(String city){
        if(!city.equalsIgnoreCase("")){
            ((EditText)findViewById(R.id.view_stations_tv_name_city)).setText(city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase());
        }
    }

    String getPref(String file, String key, String defaulValue) {
        String s = key;
        SharedPreferences preferences = getSharedPreferences(file, 0);
        return preferences.getString(s, defaulValue);
    }

    void displayStations(ArrayList<Station> stations){
        if(stations == null || stations.size() == 0)
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
            mMap.setOnCameraChangeListener(cluster);
            mMap.setOnMarkerClickListener(cluster);
        }
    }

    private void displayMarker(ArrayList<Station> stations) {
        if (mMap != null && cluster != null) {
            // Add remove Marker
            cluster.clearItems();
        } else
            return;

        if(stations == null)
            return;

        for(int i = 0; i < stations.size(); i++){
            if(stations.get(i).contractName.equalsIgnoreCase(city) || city.equalsIgnoreCase("")){
                if(stateSearch == ESearchState.TAKE){
                    if(stations.get(i).availableBike > 0){
                        cluster.addItem(new MarkerItem(stations.get(i).pos.lat, stations.get(i).pos.lng));
                    }
                } else if(stateSearch == ESearchState.RETURN){
                    if(stations.get(i).availableBikeStands > 0){
                        cluster.addItem(new MarkerItem(stations.get(i).pos.lat, stations.get(i).pos.lng));
                    }
                }
            }
        }
        cluster.cluster();

    }

    private class MarkerItem implements ClusterItem {
        private LatLng position = null;

        public MarkerItem(double lat, double lng){
            position = new LatLng(lat, lng);
        }

        @Override
        public LatLng getPosition() {
            return position;
        }


    }

    private class CustomClusterRenderer extends DefaultClusterRenderer<MarkerItem> {

        public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected void onBeforeClusterItemRendered(MarkerItem item, MarkerOptions markerOptions) {
            super.onBeforeClusterItemRendered(item, markerOptions);
            //markerOptions.title(item.getTag());
            //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bicycle_green_2_128));
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
