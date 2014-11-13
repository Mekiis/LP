package fr.videoclub;
import java.util.*;

public class Customer {
	private String _name;
	private List<Rental> _rentals = new ArrayList<Rental>();

	public Customer(String name) {
		_name = name;
	}

	public void addRental(Rental arg) {
		_rentals.add(arg);
	}

	public String getName() {
		return _name;
	}

	public String statement() {
		String result = "Rental Record for " + getName() + "\n";
		for (Rental each : _rentals) {
			// show figures for this rental
			result += "\t" + each.getMovie().getTitle() + "\t"
					+ String.valueOf(each.getCharge()) + "\n";
		}
		// add footer lines
		result += "Amount owed is " + String.valueOf(getTotalCharge()) + "\n";
		result += "You earned " + String.valueOf(getTotalFrequentRenterPoints())
				+ " frequent renter points";
		return result;

	}

	private int getTotalFrequentRenterPoints() {
		int frequentRenterPoints = 0;
		
		for (Rental rental : _rentals) {
			frequentRenterPoints += rental.getFrequentPoints();
		}
		
		return frequentRenterPoints;
	}

	private double getTotalCharge() {
		double totalAmount = 0f;
		
		for (Rental rental : _rentals) {
			totalAmount += rental.getCharge();

		}
		return totalAmount;
	}
	
	public String htmlStatement() {
        String result = "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">";
        result+= "<H1>Rentals for <EM>" + getName() + "</EM></ H1><P>\n";
        for(Rental rental : _rentals) {
            //show figures for each rental
            result += rental.getMovie().getTitle()+ ": " +
                          String.valueOf(rental.getCharge()) + "<BR>\n";
		}
        //add footer lines
        result +=  "<P>You owe <EM>" + String.valueOf(getTotalCharge()) + "</EM><P>\n";
        result += "On this rental you earned <EM>" +
            String.valueOf(getTotalFrequentRenterPoints()) +
            "</EM> frequent renter points<P>";
        return result;
	}
}
