package generic_filter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class CollectionFiltersTest {

	private List<Integer> list;
	private Map<Integer, Integer> map;
	private List<Personne> listPersonne;
	private Map<Personne, Integer> mapPersonne;
	private List<Integer> listFinale;
	private List<Personne> listPersonneFinal;
	
	@Before 
	public void setUp(){
		list = new ArrayList<Integer>();
		map = new HashMap<Integer, Integer>();
		listPersonne = new ArrayList<Personne>();
		mapPersonne = new HashMap<Personne, Integer>();
		
		listFinale = new ArrayList<Integer>();
		listPersonneFinal = new ArrayList<Personne>();
	}

	@Test
	public void testCountDuplicateIntegerSimple() {
		list = Arrays.asList(0,0);
		map.put(0, 2);
		assertEquals(map, CollectionFilters.countDuplicates(list));
	}

	@Test
	public void testCountDuplicateIntegerMultiple() {
		list = Arrays.asList(0,0,1,2,1,5,2,2,3,5);
		map.put(0, 2);
		map.put(1, 2);
		map.put(2, 3);
		map.put(5, 2);
		assertEquals(map, CollectionFilters.countDuplicates(list));
	}

	@Test
	public void testCountDuplicatePersonneSimple() {
		map.clear();
		listPersonne = Arrays.asList(
				new Personne("a", "b", "20102010"),
				new Personne("a", "b", "20102010"));
		mapPersonne.put(new Personne("a", "b", "20102010"), 2);
		assertEquals(mapPersonne, CollectionFilters.countDuplicates(listPersonne));
	}

	@Test
	public void testCountDuplicatePersonneMultiple() {
		listPersonne = Arrays.asList(
				new Personne("a", "b", "20102010"),
				new Personne("a", "c", "20102010"),
				new Personne("b", "b", "20102010"),
				new Personne("a", "c", "20102010"),
				new Personne("a", "b", "20102010"),
				new Personne("b", "b", "20102010"),
				new Personne("b", "b", "20152010"),
				new Personne("b", "b", "20152010"),
				new Personne("a", "b", "20102015"));
		mapPersonne.put(new Personne("a", "b", "20102010"), 2);
		mapPersonne.put(new Personne("a", "c", "20102010"), 2);
		mapPersonne.put(new Personne("b", "b", "20102010"), 2);
		mapPersonne.put(new Personne("b", "b", "20152010"), 2);
		assertEquals(mapPersonne, CollectionFilters.countDuplicates(listPersonne));
	}

	@Test
	public void testRemoveDuplicateIntegerSimple() {
		list = new ArrayList<Integer>(Arrays.asList(0,0));
		CollectionFilters.removeDuplicates(list);
		listFinale = Arrays.asList(0);
		assertEquals(listFinale, list);
	}

	@Test
	public void testRemoveDuplicateIntegerMultiple() {
		list = new ArrayList<Integer>(Arrays.asList(0,1,3,3,0,3,2,4,6));
		CollectionFilters.removeDuplicates(list);
		listFinale = Arrays.asList(0,1,3,2,4,6);
		assertEquals(listFinale, list);
	}

	@Test
	public void testRemoveDuplicatePersonneSimple() {
		listPersonne = new ArrayList<Personne>(Arrays.asList(
				new Personne("a", "b", "20102010"),
				new Personne("a", "b", "20102010")));
		CollectionFilters.removeDuplicates(listPersonne);
		listPersonneFinal = Arrays.asList(
				new Personne("a", "b", "20102010"));
		assertEquals(listPersonneFinal, listPersonne);
	}

	@Test
	public void testRemoveDuplicatePersonneMultiple() {
		listPersonne = new ArrayList<Personne>(Arrays.asList(
				new Personne("a", "b", "20102010"),
				new Personne("a", "c", "20102010"),
				new Personne("b", "b", "20102010"),
				new Personne("a", "c", "20102010"),
				new Personne("a", "b", "20102010"),
				new Personne("b", "b", "20102010"),
				new Personne("b", "b", "20152010"),
				new Personne("b", "b", "20152010"),
				new Personne("a", "b", "20102015")));
		CollectionFilters.removeDuplicates(listPersonne);
		listPersonneFinal = Arrays.asList(
				new Personne("a", "b", "20102010"),
				new Personne("a", "c", "20102010"),
				new Personne("b", "b", "20102010"),
				new Personne("b", "b", "20152010"),
				new Personne("a", "b", "20102015"));
		assertEquals(listPersonneFinal, listPersonne);
	}

}
