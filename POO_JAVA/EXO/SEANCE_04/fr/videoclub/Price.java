package fr.videoclub;

public abstract class Price {
	public abstract int getPriceCode();

	public abstract double getCharge(int daysRented);

	public int getFrequentPoints(int daysRented) {
		return 1;
	}
}
