package EXO_08;

import static org.junit.Assert.*;

import org.junit.Test;

public class PointTest {

	private static final Point ORIGIN = new Point(0, 0);
	private static final double EPSILON = 1e-6;

	@Test
	public void testEquals() {
		Point other = new Point(0, 0);
		assertEquals(ORIGIN, other);
		assertEquals(ORIGIN.hashCode(), other.hashCode());
	}
	
	@Test
	public void testDistance() {
		assertEquals(0.0, ORIGIN.distance(ORIGIN), EPSILON);
		distanceOrigine(new Point(1, 0), 1.0);
		distanceOrigine(new Point(1, 1), Math.sqrt(2.0));
		distanceOrigine(new Point(-1, 1), Math.sqrt(2.0));
	}

	private void distanceOrigine(Point p, double distance) {
		assertEquals(distance, ORIGIN.distance(p), EPSILON);
	}

}
