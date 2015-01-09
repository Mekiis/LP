package fr.videoclub;
public class Movie {

	public static final int CHILDRENS = 2;
	public static final int REGULAR = 0;
	public static final int NEW_RELEASE = 1;
	
	private String _title;
	private Price _price;

	public Movie(String title, int priceCode) {
		_title = title;
		setPriceCode(priceCode);
	}

	public int getPriceCode() {
		return _price.getPriceCode();
	}

	public void setPriceCode(int arg) {
		switch (arg) {
		case CHILDRENS:
			_price = new ChildrenPrice();
			break;
		case NEW_RELEASE:
			_price = new NewReleasePrice();
			break;
		case REGULAR:
			_price = new RegularPrice();
			break;
		default:
			throw new IllegalArgumentException("Incorrect price code");
		}
	}

	public String getTitle() {
		return _title;
	}

	double getCharge(int daysRented) {
		return _price.getCharge(daysRented);
	}

	public int getFrequentPoints(int daysRented) {
		return _price.getFrequentPoints(daysRented);
	}
}
