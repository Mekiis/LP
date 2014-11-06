package EXO_08;

public class Point {
	
	private final int x;
	private final int y;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Point))
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public double distance(Point otherPoint) {
		final int deltaX = x-otherPoint.x;
		final int deltaY = y-otherPoint.y;
		return Math.sqrt(deltaX*deltaX + deltaY*deltaY);
	}

}
