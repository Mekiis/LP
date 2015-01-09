package fr.videoclub;

public class HtmlStatement extends Statement {

	@Override
	protected String getEachRentalString(Rental rental) {
		return rental.getMovie().getTitle()+ ": " +
	              String.valueOf(rental.getCharge()) + "<BR>\n";
	}

	@Override
	protected String getFooter(Customer customer) {
		String result =  "<P>You owe <EM>" + String.valueOf(customer.getTotalCharge()) + "</EM><P>\n";
	    result += "On this rental you earned <EM>" +
	        String.valueOf(customer.getTotalFrequentRenterPoints()) +
	        "</EM> frequent renter points<P>";
	    return result;
	}

	@Override
	protected String getHeader(Customer customer) {
		String result = "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">";
	    result+= "<H1>Rentals for <EM>" + customer.getName() + "</EM></ H1><P>\n";
		return result;
	}

}
