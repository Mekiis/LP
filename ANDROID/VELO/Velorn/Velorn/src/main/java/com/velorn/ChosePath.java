package com.velorn;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.velorn.container.Station;
import com.velorn.loaderJSon.LoaderJSonRunnable;
import com.velorn.loaderJSon.LoaderJson;
import com.velorn.loaderJSon.LoaderJsonParams;
import com.velorn.map.ConstructPath;
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

    private static ConstructPath.EWayToTravel wayToTravel = ConstructPath.EWayToTravel.walking;

    private static TextView UITextBike;
    private static TextView UITextCar;
    private static TextView UITextFoot;
    private static ImageButton UIImageBike;
    private static ImageButton UIImageCar;
    private static ImageButton UIImageFoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_path);

        String destination = getIntent().getStringExtra(ViewStations.CHOSE_PATH_DESTINATION);
        ((AutoCompleteTextView) findViewById(R.id.chose_path_et_origin)).setText(getResources().getString(R.string.chose_path_user_position));
        ((AutoCompleteTextView) findViewById(R.id.chose_path_et_destination)).setText(destination);

        wayToTravel = ConstructPath.EWayToTravel.walking;

        UITextBike = (TextView) findViewById(R.id.chose_path_tv_way_to_travel_bike);
        UITextCar = (TextView) findViewById(R.id.chose_path_tv_way_to_travel_car);
        UITextFoot = (TextView) findViewById(R.id.chose_path_tv_way_to_travel_foot);
        UIImageBike = (ImageButton) findViewById(R.id.chose_path_way_to_travel_bike);
        UIImageCar = (ImageButton) findViewById(R.id.chose_path_way_to_travel_car);
        UIImageFoot = (ImageButton) findViewById(R.id.chose_path_way_to_travel_foot);

        displayWayToTravel();

        List<String> station = new ArrayList<String>();
        for(Station s : SplashScreen.stations.getListStations()){
            if(s.contractName.equalsIgnoreCase("lyon")){
                station.add(s.name);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, station);
        ((AutoCompleteTextView) findViewById(R.id.chose_path_et_origin)).setAdapter(adapter);
        ((AutoCompleteTextView) findViewById(R.id.chose_path_et_destination)).setAdapter(adapter);
    }

    public void onChangeWayToTravel(View v){
        switch (v.getId()){
            case R.id.chose_path_ll_way_to_travel_bike:
                wayToTravel = ConstructPath.EWayToTravel.bicycling;
                break;
            case R.id.chose_path_ll_way_to_travel_car:
                wayToTravel = ConstructPath.EWayToTravel.driving;
                break;
            case R.id.chose_path_ll_way_to_travel_foot:
                wayToTravel = ConstructPath.EWayToTravel.walking;
                break;
        }

        displayWayToTravel();
    }

    public void displayWayToTravel(){
        drawIcon(ConstructPath.EWayToTravel.bicycling, UIImageBike, UITextBike, R.drawable.icone_bike, R.drawable.icone_bike_hover );
        drawIcon(ConstructPath.EWayToTravel.driving, UIImageCar, UITextCar, R.drawable.icone_drive, R.drawable.icone_drive_hover );
        drawIcon(ConstructPath.EWayToTravel.walking, UIImageFoot, UITextFoot, R.drawable.icone_walk, R.drawable.icone_walk_hover );
    }

    private void drawIcon(ConstructPath.EWayToTravel way, ImageButton img, TextView tv, int idNormal, int idHover) {
        if(wayToTravel == way){
            tv.setTextColor(getResources().getColor(R.color.text_color_hover));
            img.setImageDrawable(getResources().getDrawable(idHover));
        } else {
            tv.setTextColor(getResources().getColor(R.color.text_color));
            img.setImageDrawable(getResources().getDrawable(idNormal));
        }
    }

    public void onLocalise(View v){
        ((EditText) findViewById(R.id.chose_path_et_origin)).setText(R.string.chose_path_user_position);
    }

    public void onValidate(View v){
        Intent returnIntent = new Intent();
        returnIntent.putExtra(ViewStations.CHOSE_PATH_ORIGIN, ((EditText) findViewById(R.id.chose_path_et_origin)).getText().toString());
        returnIntent.putExtra(ViewStations.CHOSE_PATH_DESTINATION, ((EditText) findViewById(R.id.chose_path_et_destination)).getText().toString());
        returnIntent.putExtra(ViewStations.CHOSE_PATH_WAY_TO_TRAVEL, wayToTravel.name());
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
