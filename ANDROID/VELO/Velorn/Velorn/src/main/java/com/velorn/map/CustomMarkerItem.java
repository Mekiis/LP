package com.velorn.map;

public class CustomMarkerItem implements ClusterItem {
    private LatLng position = null;
    private Station station = null;
    private ESearchState state = ESearchState.TAKE;

    public MarkerItem(Station station, ESearchState state) {
        this.station = station;
        this.position = new LatLng(this.station.pos.lat, this.station.pos.lng);
        this.state = state;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public String getTag() {
        if (state == ESearchState.TAKE) {
            return "" + this.station.availableBike;
        } else {
            return "" + this.station.availableBikeStands;
        }
    }

    public String getTitle() {
        return station.name+"\n"+station.address;
    }

    public float getColor() {
        float[] HSV = new float[3];
        if (state == ESearchState.TAKE) {
            new Color().colorToHSV(getResources().getColor(R.color.views_stations_bike_avaible), HSV);
        } else {
            new Color().colorToHSV(getResources().getColor(R.color.views_stations_place_avaible), HSV);
        }
        return HSV[0];
    }

    public Bitmap getIcon(Context context) {
        View marker = null;
        if (state == ESearchState.TAKE) {
            marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_take, null);
        } else {
            marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_return, null);
        }
        ((TextView) marker.findViewById(R.id.custom_marker_txt)).setText(getTag());
        return createDrawableFromView(context, marker);
    }

    // Convert a view to bitmap
    private Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

}