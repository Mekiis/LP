package com.velorn;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.velorn.container.Station;
import com.velorn.parser.StationParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

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
    private ArrayList<Station> stations = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Layout Management
        setContentView(R.layout.activity_view_stations_map);

        // UI Management
        loaderBar = (ProgressBar) findViewById(R.id.view_stations_loadBar);
        msgError = (TextView) findViewById(R.id.view_stations_txt_error);
        loaderBar.setVisibility(View.VISIBLE);
        msgError.setVisibility(View.GONE);

        city = getPref(Ville.CITIES, Ville.CITY_PREF, "");
        if(!city.equalsIgnoreCase(""))
            ((TextView) findViewById(R.id.view_stations_tv_name_city)).setText(city);
        else
            ((TextView) findViewById(R.id.view_stations_tv_name_city)).setText(getResources().getString(R.string.view_stations_tv_city_name_none));

        setUpMapIfNeeded();

        new LoaderJson().execute("https://api.jcdecaux.com/vls/v1/stations?apiKey=416ddf2bf645df9eea6d7c874904e5626ae62ffd", new LoaderJSonRunnable() {
            @Override
            public void run() {

            }
        });
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

    class LoaderJson extends AsyncTask<Object, Void, Object>{
        private InputStream is = null;
        private JSONObject jObj = null;
        private String json = "";
        private String url = "";
        private LoaderJSonRunnable runnable = null;

        @Override
        protected Object doInBackground(Object... obj) {
            url = (String) obj[0];
            runnable = (LoaderJSonRunnable) obj[1];

            ArrayList<Station> stations = null;

            // Making HTTP request
            try {
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet HttpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(HttpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                json = sb.toString();
                Log.d("Velorn", ""+json);
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }

            stations = new StationParser().CreateStations(json);

            Log.d("Velorn", ""+stations);

            return stations;
        }

        @Override
        protected void onPostExecute(Object a_obj) {
            super.onPostExecute(a_obj);

            runnable.run();
            stations = (ArrayList<Station>) a_obj;
            displayStations(stations);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }



    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap(ArrayList<Station>)} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link com.google.android.gms.maps.SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
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

    private void displayMarker(ArrayList<Station> stations){
        if (mMap != null && cluster != null) {
            // Add remove Marker
            cluster.clearItems();
            setUpMap(stations);
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap(ArrayList<Station> stations) {
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

    public void searchChange(View v){
        switch(v.getId()){
            case R.id.view_stations_rb_take:
                if(stateSearch != ESearchState.TAKE){
                    stateSearch = ESearchState.TAKE;
                    displayMarker(stations);
                }
                break;
            case R.id.view_stations_rb_return:
                if(stateSearch != ESearchState.RETURN){
                    stateSearch = ESearchState.RETURN;
                    displayMarker(stations);
                }
                break;
        }
    }
}
