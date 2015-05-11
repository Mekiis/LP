package os.io.geolocos.Path;

import os.io.geolocos.Container.Coordinate;

/**
 * Created by iem on 11/05/15.
 */
public class Line {
    public Coordinate p1 = null;
    public Coordinate p2 = null;
    public float weight = 1f;

    public Line(Coordinate p1, Coordinate p2, float weight) {
        this.p1 = p1;
        this.p2 = p2;
        this.weight = weight;
    }
}
