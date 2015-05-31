package fr.univ.lyon1.mastermind;

import java.util.*;
import java.util.concurrent.*;

import static fr.univ.lyon1.mastermind.Peg.*;
import static fr.univ.lyon1.mastermind.LaunderThrowable.launderThrowable;

public class Solver {

	static final int MAX_CODE_LENGTH = 8;
	private static ExecutorService executor;
	private volatile static List<Code> untriedGuesses;
	private volatile static List<Code> possibleSecrets;

	static List<Code> allCodes(int codeLength) {
		Peg[] colors = Peg.values();
		int colorCount = colors.length;

		// Allocate the list that will contain all the codes
		int nbPermutations = (int) Math.pow(colorCount, codeLength);
		List<Code> codes = new LinkedList<Code>();

		// Initial code (all RED)
		Peg[] codeArray = new Peg[codeLength];
		Arrays.fill(codeArray, RED);
		Code code = Code.valueOf(codeArray);

		for (int i = 0; i < nbPermutations; i++) {
			codes.add(code);
			code = code.nextCode();
		}

		return codes;
	}

	public static List<Code> solve(Scorer scorer) {
		//Contains the list of codes leading to the solution
		List<Code> solution = new ArrayList<>();

		final int codeLength = scorer.getCodeLength();
		if (codeLength > MAX_CODE_LENGTH) {
			throw new IllegalArgumentException("Solver max code length is 8");
		}

		untriedGuesses = allCodes(codeLength);
		possibleSecrets = new LinkedList<Code>(untriedGuesses);
		
		Code guess = initialGuess(codeLength);

		Score winningScore = Score.valueOf(codeLength, 0);
		boolean found = false;

		do {
			// Add guess to the solution
			solution.add(guess);
			// score the last guess
			Score score = scorer.score(guess);
			// Filter the list of possible secrets to remove the ones that aren't consistent with this score
			filterPossibleSecrets(possibleSecrets, guess, score);
			// Test if the search is over
			if (score.equals(winningScore)) {
				found = true;
			} else if (possibleSecrets.size() == 1) {
				solution.add(possibleSecrets.get(0));
				found = true;
			} else {
				// Remove the last guess from the untriedGuesses (optimization : remember the index)
				untriedGuesses.remove(guess);
				
				//TODO: Evaluate concurrently untriedGuesses and pick the most interesting one
				CompletionService<Score> completionService = new ExecutorCompletionService<Score>(executor);
				for (final Code codeGuess : untriedGuesses)
					completionService.submit(new Callable<Score>() { public Score call() { return Scorer.score(guess, codeGuess); } });
				try {
					//TODO: Pick the untried guess whose evaluation gives the lowest number
					
					for(int t = 0; t < untriedGuesses.size(); t++){
						Future<Score> f = completionService.take();
						Score scoreGet = f.get();
						if(scoreGet.getBlackCount())
					}
					
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} catch (ExecutionException e) {
					throw launderThrowable(e.getCause());
				}

			}
		} while (!found);

		executor.shutdown();
		return solution;
	}

	static int maximum(int[] scoreHits) {
		int max = 0;
		for (int score : scoreHits) {
			max = (score > max) ? score : max;
		}
		return max;
	}

	private static void filterPossibleSecrets(List<Code> possibleSecrets, Code guess, Score score) {
		//TODO: à implémenter
	}

	static Code initialGuess(int codeLength) {
		Peg[] initialValue = new Peg[codeLength];
		int i = 0;
		while (i < Math.ceil(codeLength / 2.0)) {
			initialValue[i] = RED;
			i++;
		}
		while (i < initialValue.length) {
			initialValue[i] = GREEN;
			i++;
		}
		return Code.valueOf(initialValue);
	}

}
