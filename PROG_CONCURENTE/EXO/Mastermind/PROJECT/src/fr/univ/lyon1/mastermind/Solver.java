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
		executor = Executors.newCachedThreadPool();

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
				final Code guessCode = Code.valueOf(guess.asArray());
				//TODO: Evaluate concurrently untriedGuesses and pick the most interesting one
				CompletionService<CodeMaxUncertainty> completionService = new ExecutorCompletionService<CodeMaxUncertainty>(executor);
				for (final Code untriedGuess : untriedGuesses)
					completionService.submit(new Callable<CodeMaxUncertainty>() { 
						public CodeMaxUncertainty call() {
							Score s = Scorer.score(guessCode, untriedGuess);
							return new CodeMaxUncertainty(untriedGuess, s.getBlackCount() * (MAX_CODE_LENGTH * 2) + s.getWhiteCount()); 
						} 
					});
				try {
					//TODO: Pick the untried guess whose evaluation gives the lowest number
					int lowestNumber = score.getBlackCount() * (MAX_CODE_LENGTH * 2) + score.getWhiteCount();
					for(int t = 0; t < untriedGuesses.size(); t++){
						Future<CodeMaxUncertainty> f = completionService.take();
						CodeMaxUncertainty codeGet = f.get();
						if(codeGet.getAmbiguity() < lowestNumber){
							lowestNumber = codeGet.getAmbiguity();
							guess = codeGet.getCode();
						}
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
		int valueScore = score.getBlackCount() * (MAX_CODE_LENGTH * 2) + score.getWhiteCount();
		
		Iterator<Code> iter = possibleSecrets.iterator();

		while (iter.hasNext()) {
		    Code code = iter.next();

		    Score scorePossible = Scorer.score(guess, code);
			int valueScorePossible = scorePossible.getBlackCount() * (MAX_CODE_LENGTH * 2) + scorePossible.getWhiteCount();
			if(valueScorePossible > valueScore)
			{
				iter.remove();
			}
		}
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
