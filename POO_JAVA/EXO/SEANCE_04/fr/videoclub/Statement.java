package fr.videoclub;

public abstract class Statement {

	public String getStatement(Customer customer) {
	    String result = getHeader(customer);
	    for(Rental rental : customer._rentals) {
	        //show figures for each rental
	        result += getEachRentalString(rental);
		}
	    //add footer lines
	    return result += getFooter(customer);
	}

	protected abstract String getFooter(Customer customer);

	protected abstract String getEachRentalString(Rental rental);

	protected abstract String getHeader(Customer customer);

}
