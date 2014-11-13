package fr.videoclub;

public class ChildrenPrice extends Price {

	@Override
	public int getPriceCode() {
		return Movie.CHILDRENS;
	}

}
