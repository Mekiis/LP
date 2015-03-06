package os.io.geolocos.Container;

/**
 * Created by iem on 10/02/15.
 */
public class Coordinate {

    private String id = "";
    private double latitude;
    private String latitudeSexa;
    private double longitude;
    private String longitudeSexa;

    public Coordinate(String id, double latitude, String latitudeSexa, double longitude, String longitudeSexa) {
        this.id = id;
        this.latitude = latitude;
        this.latitudeSexa = latitudeSexa;
        this.longitude = longitude;
        this.longitudeSexa = longitudeSexa;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatitudeSexa() {
        return latitudeSexa;
    }

    public void setLatitudeSexa(String latitudeSexa) {
        this.latitudeSexa = latitudeSexa;
    }

    public String getLongitudeSexa() {
        return longitudeSexa;
    }

    public void setLongitudeSexa(String longitudeSexa) {
        this.longitudeSexa = longitudeSexa;
    }
}
