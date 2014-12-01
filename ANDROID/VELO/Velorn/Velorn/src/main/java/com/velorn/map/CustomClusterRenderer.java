package com.velorn.map;

public class CustomClusterRenderer extends DefaultClusterRenderer<MarkerItem> {

    public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(MarkerItem item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);
        markerOptions.title(item.getTitle());
        //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(item.getColor()));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(item.getIcon(context)));
    }
}