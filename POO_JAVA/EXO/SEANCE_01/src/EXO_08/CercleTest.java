package EXO_08;

import static org.junit.Assert.*;

import org.junit.Test;

public class CercleTest {
	private final Cercle cercle = new Cercle(0, 0, 1.0);
	private static final Point ORIGIN = new Point(0, 0);
	
	@Test
	public void testCercle() {
		Cercle other = new Cercle(0, 0, 1.0);
		assertEquals(cercle, other);
		assertEquals(cercle.hashCode(), other.hashCode());
	}
	
	@Test
	public void testContains(){
		assertEquals(true, cercle.contains(ORIGIN));
	}

}
