package os.io.geolocos.Container;


import java.io.Serializable;

public class SVGPoint{
    public int rayon = 10;
    public int strokeWidth = 1;
    private String id = "";
    public String strokeColor = "black";
    public String fillColor = "red";
    public String tag = "";

    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public SVGPoint(String id, double x, double y, String tag) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.tag = tag;
    }

    public SVGPoint(String id, double x, double y, String strokeColor, int strokeWidth, String fillColor, int rayon, String tag) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.fillColor = fillColor;
        this.strokeWidth = strokeWidth;
        this.strokeColor = strokeColor;
        this.rayon = rayon;
        this.tag = tag;
    }

    public String getId() {
        return id;
    }
}
