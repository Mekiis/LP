package fr.videoclub;

public class NewReleasePrice extends Price {

	@Override
	public int getPriceCode() {
		return Movie.NEW_RELEASE;
	}

}
