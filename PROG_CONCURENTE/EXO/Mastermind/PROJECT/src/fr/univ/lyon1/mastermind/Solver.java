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
		
		//***//
		//contiendra les scores correspondants aux codes possibles
		Map<Score,List<Code>> possibleScores = new HashMap<Score,List<Code>>();
		//pour chaque code non essay�, on m�morise le couple "secret possible/scores correspondants"
		Map<Code,Map<Score,List<Code>>> combineScoreUncertainity = new HashMap<Code,Map<Score,List<Code>>>();
		//***//
		do {

			// Add guess to the solution
			solution.add(guess);
			
			// score the last guess
			Score score = scorer.score(guess);			
			
			// Filter the list of possible secrets to remove the ones that aren't consistent with this score
			Solver.filterPossibleSecrets(possibleSecrets, guess, score);
			
			// Test if the search is over
			if (score.equals(winningScore)) {
				found = true;
			} else if (possibleSecrets.size() == 1) {
				solution.add(possibleSecrets.get(0));
				found = true; 
			} else {
				// Remove the last guess from the untriedGuesses (optimization : remember the index)
				untriedGuesses.remove(guess);
				
				combineScoreUncertainity.clear();
				possibleScores.clear();
				
				CompletionService<Map<Code,Map<Score,List<Code>>>> completionService = new ExecutorCompletionService<Map<Code,Map<Score,List<Code>>>>(executor);
				
				for(final Code possibleGuess : untriedGuesses){
					completionService.submit(new Callable<Map<Code,Map<Score,List<Code>>>>() {
						
						@Override
						public Map<Code, Map<Score, List<Code>>> call() throws Exception {
															
							Map<Score,List<Code>> possibleScores = new HashMap<Score,List<Code>>();
							
							possibleScores.clear();
							for(Code possibleSecret : possibleSecrets){
								Score aScore = Scorer.score(possibleGuess, possibleSecret);
								if(!possibleScores.containsKey(aScore)){
									List<Code> solList = new ArrayList<Code>();
									solList.add(possibleSecret);
									possibleScores.put(aScore,solList);
								}else{
									(possibleScores.get(aScore)).add(possibleGuess);
								}
							}
							Map<Code,Map<Score,List<Code>>> scoreUncertainity = new HashMap<Code,Map<Score,List<Code>>>();
							scoreUncertainity.put(possibleGuess,possibleScores);
							
							return scoreUncertainity;
						}
					});
				}
				
				try {
					for(int t = 0; t < untriedGuesses.size(); t++){
						Future<Map<Code,Map<Score,List<Code>>>> f = completionService.take();
						Map<Code,Map<Score,List<Code>>> data = f.get();
						for(Code aCode : data.keySet()){
							combineScoreUncertainity.put(aCode, data.get(aCode));
						}
					}
					List<CodeMaxUncertainty> incertitudeList = new ArrayList<CodeMaxUncertainty>();
					
					for(Code codeKey : combineScoreUncertainity.keySet()){
						Map<Score, List<Code>> matches =  combineScoreUncertainity.get(codeKey);
						CodeMaxUncertainty cmu = new CodeMaxUncertainty(null, Integer.MAX_VALUE);
						for(Score scoreKey : matches.keySet()){
							int numberOfSolutions = matches.get(scoreKey).size();
							if(numberOfSolutions < cmu.getAmbiguity()){
								cmu = new CodeMaxUncertainty(codeKey, numberOfSolutions);
							}
						}
						incertitudeList.add(cmu);
					}
					
					CodeMaxUncertainty nextTry = new CodeMaxUncertainty(null,Integer.MAX_VALUE);
					
					for(CodeMaxUncertainty cmuu : incertitudeList){
						if (cmuu.getAmbiguity() < nextTry.getAmbiguity()){
							nextTry = cmuu;
						}
					}
					
					guess = nextTry.getCode();
				
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
		List<Code> impossibleCodes = new ArrayList<Code>();
		for (Code possibleSecret : possibleSecrets){
			Score tempScore = Scorer.score(guess,possibleSecret);
			if(score.getBlackCount() != tempScore.getBlackCount() ||
					score.getWhiteCount() != tempScore.getWhiteCount()){
				impossibleCodes.add(possibleSecret);
			}
		}
		possibleSecrets.removeAll(impossibleCodes);
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
