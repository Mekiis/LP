package fr.videoclub;
import java.util.*;

public class Customer {
	private String _name;
	List<Rental> _rentals = new ArrayList<Rental>();

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
		return new TextStatement().getStatement(this);
	}

	public String htmlStatement() {
		return new HtmlStatement().getStatement(this);
	}

	int getTotalFrequentRenterPoints() {
		int frequentRenterPoints = 0;
		
		for (Rental rental : _rentals) {
			frequentRenterPoints += rental.getFrequentPoints();
		}
		
		return frequentRenterPoints;
	}

	double getTotalCharge() {
		double totalAmount = 0f;
		
		for (Rental rental : _rentals) {
			totalAmount += rental.getCharge();

		}
		return totalAmount;
	}
}
