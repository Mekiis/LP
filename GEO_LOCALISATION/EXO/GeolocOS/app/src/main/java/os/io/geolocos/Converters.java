package os.io.geolocos;

import java.util.ArrayList;
import java.util.List;

import os.io.geolocos.Container.Coordinate;
import os.io.geolocos.Container.SVGPoint;

/**
 * Created by iem on 04/03/15.
 */
public class Converters {

    public static List<SVGPoint> coordinates2SVGPoints(List<Coordinate> coordinates, int width, int height) {
        List<SVGPoint> points = new ArrayList<>();
        double pointLeft = Double.MAX_VALUE;
        double pointRight = Double.MAX_VALUE * -1;
        double pointBottom = Double.MAX_VALUE;
        double pointTop = Double.MAX_VALUE * -1;

        for (Coordinate c : coordinates){
            if(c.getLongitude() < pointLeft)
                pointLeft = c.getLongitude();
            if(c.getLongitude() > pointRight)
                pointRight = c.getLongitude();
            if(c.getLatitude() < pointBottom)
                pointBottom = c.getLatitude();
            if(c.getLatitude() > pointTop)
                pointTop = c.getLatitude();
        }

        for (Coordinate c : coordinates){
            double lat = (c.getLatitude() - pointBottom) * height / (pointTop - pointBottom);
            double lng = (c.getLongitude() - pointLeft) * width / (pointRight - pointLeft);;
            points.add(new SVGPoint(lat, lng));
        }

        return points;
    }

    public static String exportSVG(int width, int height, List<SVGPoint> points) {
        String svg = "<?xml version=\"1.0\" standalone=\"yes\"?>";
        svg += "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">";
        svg += "<svg width=\""+width+"px\" height=\""+height+"px\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">";

        for(int i = 0; i < points.size(); i++) {
            svg += "<circle cx=\""+points.get(i).getX()+"\" cy=\""+points.get(i).getY()+"\"";
            svg += " r=\""+points.get(i).rayon+"\" stroke=\""+points.get(i).strokeColor+"\" stroke-width=\""+points.get(i).strokeWidth+"\" fill=\""+points.get(i).fillColor+"\" />";
        }
        svg += "<rect x=\"0\" y=\"0\" width=\""+width+"\" height=\""+height+"\" style=\"stroke: #009900; stroke-width: 3; stroke-dasharray: 10 5; fill: none;\"/>";
        svg += "</svg>";

        return svg;
    }

    public static double SexaToDecimal(String coordinates) {

        double degrees = 0;
        double minutes = 0;
        double seconds = 0;

        String[] coo = coordinates.split(" ");

        if(coo.length > 1)
            degrees = Double.parseDouble(coo[0]);

        if(coo.length > 2)
            minutes = Double.parseDouble(coo[1]);

        if(coo.length > 3)
            seconds = Double.parseDouble(coo[2]);

        double decimal = -1;

        decimal = degrees;
        decimal += minutes / 60.0f;
        decimal += seconds / 3600.0f;

        return decimal;
    }
}
