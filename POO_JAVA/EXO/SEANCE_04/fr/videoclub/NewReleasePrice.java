package fr.videoclub;

public class NewReleasePrice extends Price {

	@Override
	public int getPriceCode() {
		return Movie.NEW_RELEASE;
	}

	@Override
	public double getCharge(int daysRented) {
		return daysRented * 3;
	}

	@Override
	public int getFrequentPoints(int daysRented) {
		return daysRented > 1 ? 2 : 1;
	}

}
