package EXO_08;

public class Rectangle implements Shape {

	private final Point point;
	private final int width;
	private final int height;

	public Rectangle(Point point, int width, int height) {
		this.point = point;
		this.width = width;
		this.height = height;
	}

	public Point getPoint() {
		return point;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public boolean contains(Point p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double area() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + ((point == null) ? 0 : point.hashCode());
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Rectangle))
			return false;
		Rectangle other = (Rectangle) obj;
		if (height != other.height)
			return false;
		if (point == null) {
			if (other.point != null)
				return false;
		} else if (!point.equals(other.point))
			return false;
		if (width != other.width)
			return false;
		return true;
	}

}
