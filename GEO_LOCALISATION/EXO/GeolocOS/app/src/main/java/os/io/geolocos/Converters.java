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
        List<Coordinate> coor = new ArrayList<>();
        for (Coordinate c : coordinates){
            coor.add(new Coordinate(c.getId(), c.getLatitude(), c.getLatitudeSexa(), c.getLongitude(), c.getLongitudeSexa()));
        }
        List<SVGPoint> points = new ArrayList<>();
        double pointLeft = Double.MAX_VALUE;
        double pointRight = Double.MAX_VALUE * -1;
        double pointBottom = Double.MAX_VALUE;
        double pointTop = Double.MAX_VALUE * -1;

        for (Coordinate c : coor){
            if(c.getLongitude() < pointLeft)
                pointLeft = c.getLongitude();
            if(c.getLongitude() > pointRight)
                pointRight = c.getLongitude();
            if(c.getLatitude() < pointBottom)
                pointBottom = c.getLatitude();
            if(c.getLatitude() > pointTop)
                pointTop = c.getLatitude();
        }

        double percent = 10.0;

        // Add marges
        coor.add(new Coordinate("M", pointBottom + ((pointBottom - pointTop) * percent / 100.0), "", pointLeft - ((pointRight - pointLeft) * percent / 100.0), ""));
        coor.add(new Coordinate("M", pointBottom + ((pointBottom - pointTop) * percent / 100.0), "", pointRight + ((pointRight - pointLeft) * percent / 100.0), ""));
        coor.add(new Coordinate("M", pointTop - ((pointBottom - pointTop) * percent / 100.0), "", pointLeft - ((pointRight - pointLeft) * percent / 100.0), ""));
        coor.add(new Coordinate("M", pointTop - ((pointBottom - pointTop) * percent / 100.0), "", pointRight + ((pointRight - pointLeft) * percent / 100.0), ""));


        pointLeft = Double.MAX_VALUE;
        pointRight = Double.MAX_VALUE * -1;
        pointBottom = Double.MAX_VALUE;
        pointTop = Double.MAX_VALUE * -1;

        for (Coordinate c : coor){
            if(c.getLongitude() < pointLeft)
                pointLeft = c.getLongitude();
            if(c.getLongitude() > pointRight)
                pointRight = c.getLongitude();
            if(c.getLatitude() < pointBottom)
                pointBottom = c.getLatitude();
            if(c.getLatitude() > pointTop)
                pointTop = c.getLatitude();
        }

        double a = height / (pointBottom - pointTop);
        double b = 0 - a * pointTop;

        double e = width / (pointRight - pointLeft);
        double f = 0 - e * pointLeft;

        for(Coordinate c : coor){
            double lat = a * c.getLatitude() + b;
            double lng = e * c.getLongitude() + f;
            if(c.getId().compareToIgnoreCase("M") == 0)
                points.add(new SVGPoint("", lat, lng, "grey", 1, "grey", 5));
            else
                points.add(new SVGPoint(c.getId(), lat, lng));
        }

        return points;
    }

    public static String exportKML(List<Coordinate> points) {
        String kml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://earth.google.com/kml/2.1\">\n" +
                "\t<Document>\n" +
                "\t\t<name>Exemple KML 3</name>\n";

        for(int i = 0; i < points.size(); i++) {
            kml += "\t\t<Placemark>";
            kml += "\t\t\t<name>"+points.get(i).getId()+"</name>\n" +
                    "\t\t\t<Style>\n" +
                    "\t\t\t\t<IconStyle>\n" +
                    "\t\t\t\t\t<Icon>\n" +
                    "\t\t\t\t\t\t<href>http://maps.google.com/mapfiles/kml/shapes/homegardenbusiness.png</href>\n" +
                    "\t\t\t\t\t</Icon>\n" +
                    "\t\t\t\t</IconStyle>\n" +
                    "\t\t\t</Style>\n" +
                    "\t\t\t<Point>";
            kml += "\t\t\t\t<coordinates>"+points.get(i).getLongitude()+","+points.get(i).getLatitude()+",0</coordinates>";
            kml += "\t\t\t</Point>\n" +
                    "\t\t</Placemark>";
        }

        kml += "\t</Document>\n" +
                "</kml>";

        return kml;
    }

    public static String exportSVG(int width, int height, List<SVGPoint> points) {
        String svg = "<?xml version=\"1.0\" standalone=\"yes\"?>";
        svg += "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">";
        svg += "<svg width=\""+width+"px\" height=\""+height+"px\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">";

        for(int i = 0; i < points.size(); i++) {
            svg += "<circle cx=\""+points.get(i).getX()+"\" cy=\""+points.get(i).getY()+"\"";
            svg += " r=\""+points.get(i).rayon+"\" stroke=\""+points.get(i).strokeColor+"\" stroke-width=\""+points.get(i).strokeWidth+"\" fill=\""+points.get(i).fillColor+"\" />";
            svg += "<text x=\""+points.get(i).getX()+"\" y=\""+points.get(i).getY()+"\" fill=\"black\">"+points.get(i).getId()+"</text>";
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
