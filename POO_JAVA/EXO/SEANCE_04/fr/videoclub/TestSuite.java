package fr.videoclub;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSuite {
	
	Customer johnDoe;

	@Before
	public void setUp() throws Exception {
		
		//Create a bunch of movies
				List<String> movieTitles = Arrays.asList("Les bronzes font du ski", "Ca tourne à Manhatan", 
						"Les tontons flingueurs","High Fidelity", "Two days in Paris", "Tous les soleils",
						"Les émotifs anonymes", "Le nom des gens", "Cars");
				int[]priceCodes = {Movie.REGULAR, Movie.REGULAR, Movie.REGULAR,
						Movie.REGULAR, Movie.REGULAR, Movie.NEW_RELEASE, Movie.NEW_RELEASE,
						Movie.NEW_RELEASE,Movie.REGULAR, Movie.CHILDRENS};
				
				List<Movie> movies = new ArrayList<Movie>();
						
				int i = 0;
				for(String title : movieTitles){
					movies.add(new Movie(title,priceCodes[i++]));
				}
				
				//Create a customer
				johnDoe = new Customer("John Doe");
				
				//Create rentals
				int nbExtraDays = 0;
				for(Movie movie : movies){
					int nbDays = 2 + nbExtraDays;
					johnDoe.addRental(new Rental(movie, nbDays));
					nbExtraDays = (nbExtraDays + 1) % 3;
				}	
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
String res = "Rental Record for John Doe"+ "\n\t"+ "Les bronzes font du ski	2.0"+ "\n\t"+ "Ca tourne à Manhatan	3.5"
		+ "\n\t"+ "Les tontons flingueurs	5.0" + "\n\t"+ "High Fidelity	2.0" + "\n\t"+ "Two days in Paris	3.5"
		+ "\n\t"+ "Tous les soleils	12.0" + "\n\t"+ "Les émotifs anonymes	6.0" + "\n\t"+ "Le nom des gens	9.0"
		+ "\n\t"+ "Cars	5.0"+ "\n"+ "Amount owed is 48.0" + "\n"+ "You earned 12 frequent renter points";
		assertEquals("Statement broken", res, johnDoe.statement());
	}
	
	private void initialStatementPrinting() throws Exception{
		setUp();
		System.out.println(johnDoe.statement());
	}
	
	public static void main(String[] args) throws Exception {
		new TestSuite().initialStatementPrinting();
	}

}
