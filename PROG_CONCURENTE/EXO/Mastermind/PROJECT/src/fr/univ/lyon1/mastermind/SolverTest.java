package fr.univ.lyon1.mastermind;

import static org.junit.Assert.*;
import static fr.univ.lyon1.mastermind.Peg.*;

import java.util.List;

import org.junit.Test;

public class SolverTest {

	@Test
	public void testInitialGuess() {
		assertEquals(Code.valueOf(RED), Solver.initialGuess(1));
		assertEquals(Code.valueOf(RED,GREEN), Solver.initialGuess(2));
		assertEquals(Code.valueOf(RED,RED,GREEN), Solver.initialGuess(3));
		assertEquals(Code.valueOf(RED,RED,GREEN,GREEN), Solver.initialGuess(4));
		assertEquals(Code.valueOf(RED,RED,RED,GREEN,GREEN), Solver.initialGuess(5));
	}
	
	@Test
	public void testMaximum(){
		int[] test = {3, 1, 5, 14, 9};
		assertEquals(14, Solver.maximum(test));
	}
	
	@Test
	public void testSolution() {
		//TODO: à compléter
		
	}
	

}
