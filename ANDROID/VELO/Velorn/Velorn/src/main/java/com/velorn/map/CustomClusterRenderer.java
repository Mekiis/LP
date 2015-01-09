package com.velorn.map;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class CustomClusterRenderer extends DefaultClusterRenderer<CustomMarkerItem> {
    private Context ctx;

    public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager clusterManager) {
        super(context, map, clusterManager);

        this.ctx = context;
    }

    @Override
    protected void onBeforeClusterItemRendered(CustomMarkerItem item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);
        markerOptions.title(item.getTitle());
        //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(item.getColor()));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(item.getIcon()));
    }
}