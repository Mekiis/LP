package com.velorn.map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.velorn.R;
import com.velorn.SplashScreen;
import com.velorn.ViewStations;
import com.velorn.container.Station;

public class CustomMarkerItem implements ClusterItem {
    private LatLng position = null;
    private Station station = null;
    private ViewStations.ESearchState state = ViewStations.ESearchState.TAKE;
    private Context ctx;

    public CustomMarkerItem(Station station, ViewStations.ESearchState state, Context ctx) {
        this.station = station;
        this.position = new LatLng(this.station.pos.lat, this.station.pos.lng);
        this.state = state;
        this.ctx = ctx;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public String getTitle() {
        return station.name;
    }

    public String getSnippet(){
       return station.address;
    }

    public float getColor() {
        float[] HSV = new float[3];
        if (state == ViewStations.ESearchState.TAKE) {
            new Color().colorToHSV(ctx.getResources().getColor(R.color.views_stations_bike_avaible), HSV);
        } else {
            new Color().colorToHSV(ctx.getResources().getColor(R.color.views_stations_place_avaible), HSV);
        }
        return HSV[0];
    }

    public Bitmap getIcon() {
        if (state == ViewStations.ESearchState.TAKE) {
            return SplashScreen.markerImage.get(this.station.availableBike-1);
        } else {
            return SplashScreen.markerImage.get(SplashScreen.NB_MARKER + this.station.availableBikeStands-2);
        }

    }

}