package EXO_08;

import static org.junit.Assert.*;

import org.junit.Test;

public class RectangleTest {

	@Test
	public void test() {
		Rectangle rect = new Rectangle(new Point(0, 0), 10, 10);
		Rectangle rect2 = new Rectangle(new Point(0, 0), 10, 10);
		assertEquals(rect, rect2);
		assertEquals(rect.hashCode(), rect2.hashCode());
	}

}
