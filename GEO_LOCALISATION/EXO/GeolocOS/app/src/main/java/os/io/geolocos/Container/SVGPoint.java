package os.io.geolocos.Container;


public class SVGPoint{
    public int rayon = 10;
    public int strokeWidth = 1;
    public String strokeColor = "black";
    public String fillColor = "red";

    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public SVGPoint(double x, double y) {
        this.x = x;

        this.y = y;
    }
}
