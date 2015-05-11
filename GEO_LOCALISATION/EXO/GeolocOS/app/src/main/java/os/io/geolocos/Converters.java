package os.io.geolocos;

import java.util.ArrayList;
import java.util.List;

import os.io.geolocos.Container.Coordinate;
import os.io.geolocos.Container.SVGPoint;

/**
 * Created by iem on 04/03/15.
 */
public class Converters {

    public static class SVGDataContainer{
        public List<SVGPoint> points = null;

        public Double pointLeft = Double.MAX_VALUE;
        public Double pointRight = Double.MAX_VALUE * -1;
        public Double pointBottom = Double.MAX_VALUE;
        public Double pointTop = Double.MAX_VALUE * -1;

        public double a;
        public double b;

        public double e;
        public double f;
    }

    public static SVGDataContainer createSVGData(List<Coordinate> coordinates, int width, int height){
        List<Coordinate> coor = new ArrayList<>();
        for (Coordinate c : coordinates){
            coor.add(new Coordinate(c.getId(), c.getLatitude(), c.getLatitudeSexa(), c.getLongitude(), c.getLongitudeSexa()));
        }

        SVGDataContainer data = new SVGDataContainer();

        data.pointLeft = Double.MAX_VALUE;
        data.pointRight = Double.MAX_VALUE * -1;
        data.pointBottom = Double.MAX_VALUE;
        data.pointTop = Double.MAX_VALUE * -1;

        for (Coordinate c : coor){
            if(c.getLongitude() < data.pointLeft)
                data.pointLeft = c.getLongitude();
            if(c.getLongitude() > data.pointRight)
                data.pointRight = c.getLongitude();
            if(c.getLatitude() < data.pointBottom)
                data.pointBottom = c.getLatitude();
            if(c.getLatitude() > data.pointTop)
                data.pointTop = c.getLatitude();
        }

        double percent = 10.0;

        // Add marges
        coor.add(new Coordinate("M", data.pointBottom + ((data.pointBottom - data.pointTop) * percent / 100.0), "", data.pointLeft - ((data.pointRight - data.pointLeft) * percent / 100.0), ""));
        coor.add(new Coordinate("M", data.pointBottom + ((data.pointBottom - data.pointTop) * percent / 100.0), "", data.pointRight + ((data.pointRight - data.pointLeft) * percent / 100.0), ""));
        coor.add(new Coordinate("M", data.pointTop - ((data.pointBottom - data.pointTop) * percent / 100.0), "", data.pointLeft - ((data.pointRight - data.pointLeft) * percent / 100.0), ""));
        coor.add(new Coordinate("M", data.pointTop - ((data.pointBottom - data.pointTop) * percent / 100.0), "", data.pointRight + ((data.pointRight - data.pointLeft) * percent / 100.0), ""));


        data.pointLeft = Double.MAX_VALUE;
        data.pointRight = Double.MAX_VALUE * -1;
        data.pointBottom = Double.MAX_VALUE;
        data.pointTop = Double.MAX_VALUE * -1;

        for (Coordinate c : coor){
            if(c.getLongitude() < data.pointLeft)
                data.pointLeft = c.getLongitude();
            if(c.getLongitude() > data.pointRight)
                data.pointRight = c.getLongitude();
            if(c.getLatitude() < data.pointBottom)
                data.pointBottom = c.getLatitude();
            if(c.getLatitude() > data.pointTop)
                data.pointTop = c.getLatitude();
        }

        data.a = height / (data.pointBottom - data.pointTop);
        data.b = 0 - data.a * data.pointTop;

        data.e = width / (data.pointRight - data.pointLeft);
        data.f = 0 - data.e * data.pointLeft;

        return data;
    }

    public static SVGDataContainer coordinates2SVGPoints(List<Coordinate> coordinates, SVGDataContainer data, String tag) {
        List<Coordinate> coor = new ArrayList<>();
        for (Coordinate c : coordinates){
            coor.add(new Coordinate(c.getId(), c.getLatitude(), c.getLatitudeSexa(), c.getLongitude(), c.getLongitudeSexa()));
        }

        for(Coordinate c : coor){
            double lat = data.a * c.getLatitude() + data.b;
            double lng = data.e * c.getLongitude() + data.f;
            if(c.getId().compareToIgnoreCase("M") == 0)
                data.points.add(new SVGPoint("", lat, lng, "grey", 1, "grey", 5, tag));
            else
                data.points.add(new SVGPoint(c.getId(), lat, lng, tag));
        }

        return data;
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
        svg += "<svg x=\"0px\" y=\"0px\" width=\"100%\" height=\"100%\" viewBox=\"0 0 "+width+" "+height+"\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">";

        for(int i = 0; i < points.size(); i++) {
            if(points.get(i).tag != "L"){
                svg += "<circle cx=\""+points.get(i).getX()+"\" cy=\""+points.get(i).getY()+"\"";
                svg += " r=\""+points.get(i).rayon+"\" stroke=\""+points.get(i).strokeColor+"\" stroke-width=\""+points.get(i).strokeWidth+"\" fill=\""+points.get(i).fillColor+"\" />";
                svg += "<text x=\""+points.get(i).getX()+"\" y=\""+points.get(i).getY()+"\" fill=\"black\">"+points.get(i).getId()+"</text>";
            } else if(i < points.size()-1) {
                svg += "<line x1=\""+points.get(i).getX()+"\" y1=\""+points.get(i).getY()+"\" x2=\""+points.get(i+1).getX()+"\" y2=\""+points.get(i+1).getY()+"\" style=\"stroke:rgb(255,0,0);stroke-width:2\" />";
            }
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
