package EXO_08;

public class Cercle implements Shape {

	private final int x;
	private final int y;
	private final double rayon;

	public Cercle(int x, int y, double rayon) {
		this.x = x;
		this.y = y;
		this.rayon = rayon;
	}

	@Override
	public boolean contains(Point p) {
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
		long temp;
		temp = Double.doubleToLongBits(rayon);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (!(obj instanceof Cercle))
			return false;
		Cercle other = (Cercle) obj;
		if (Double.doubleToLongBits(rayon) != Double
				.doubleToLongBits(other.rayon))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
