package fr.videoclub;

public class TextStatement extends Statement {

	@Override
	protected String getFooter(Customer customer) {
		String result = "Amount owed is " + String.valueOf(customer.getTotalCharge()) + "\n";
		result += "You earned " + String.valueOf(customer.getTotalFrequentRenterPoints())
				+ " frequent renter points";
		return result;
	}

	@Override
	protected String getEachRentalString(Rental each) {
		return "\t" + each.getMovie().getTitle() + "\t"
				+ String.valueOf(each.getCharge()) + "\n";
	}

	@Override
	protected String getHeader(Customer customer) {
		return "Rental Record for " + customer.getName() + "\n";
	}

}
