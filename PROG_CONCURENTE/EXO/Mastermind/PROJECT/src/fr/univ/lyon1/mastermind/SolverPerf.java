package fr.univ.lyon1.mastermind;

import static fr.univ.lyon1.mastermind.Peg.BLUE;
import static fr.univ.lyon1.mastermind.Peg.GREEN;
import static fr.univ.lyon1.mastermind.Peg.PURPLE;
import static fr.univ.lyon1.mastermind.Peg.RED;

import java.util.Collections;
import java.util.List;

public class SolverPerf {

	public static void main(String[] args) {
		List<Code> sol = null;
		
		long [] runTimes = new long[100];
		
		for (int i = 0; i < runTimes.length; i++) {
			long start = System.nanoTime();
			sol = Solver.solve(new Scorer(Code.valueOf(RED,GREEN,PURPLE,BLUE)));
			long end = System.nanoTime();
			runTimes[i] = end-start;
		}
		
		System.out.println("Solution found in : " + min(runTimes) / 1e6 +" ms (min)");
		System.out.println("Solution found in : " + average(runTimes) / 1e6 +" ms (average)");
		System.out.println("Solution found in : " + max(runTimes) / 1e6 +" ms (max)");
		System.out.println(sol);
		System.out.println("Nb coeurs: " + Runtime.getRuntime().availableProcessors());
		
	}
	
	private static long min(long[] array) {
		long min = Long.MAX_VALUE;
		for (long val : array) {
			min = Math.min(min,val);
		}
		return min;
	}
	
	private static double average(long[] array) {
		double sum = 0;
		for (long val : array) {
			sum += val;
		}
		
		return sum / array.length;
	}

	private static long max(long[] array) {
		long max = Long.MIN_VALUE;
		for (long val : array) {
			max = Math.max(max,val);
		}
		return max;	
	}

}
