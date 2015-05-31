package fr.univ.lyon1.mastermind;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Scorer {
	private final Code secret;
	
	public Scorer(Code secret) {
		super();
		this.secret = secret;
	}
	
	public Score score(Code guess) {
		return score(guess,secret);
	}

	public static Score score(Code guess, Code secret) {
			int blackCount = exactMatches(guess,secret);
			int whiteCount = matches(guess,secret) - blackCount;
			return Score.valueOf(blackCount, whiteCount);
	}

	public int getCodeLength() {
		return secret.length();
	}
	
	private static int exactMatches(Code guess, Code secret) {
		int whiteHits = 0;
		
		for(int i = 0; i < secret.asArray().length; i++)
		{
			if(secret.asArray()[i].compareTo(guess.asArray()[i]) == 0)
				whiteHits++;
		}
		
		return whiteHits;
	}

	private static int matches(Code guess, Code secret) {
		int matchesCount = 0;
		
		boolean[] matches = new boolean[guess.asArray().length];
		
		// Count black
		for(int i = 0; i < guess.asArray().length; i++) {
			if(guess.asArray()[i].compareTo(secret.asArray()[i]) == 0) {
				matches[i] = true;
				matchesCount++;
			}	
		}
		
		// Count white
		for(int i = 0; i < guess.asArray().length; i++) {
			if(guess.asArray()[i].compareTo(secret.asArray()[i]) != 0) {
				for(int j = 0; j < secret.asArray().length; j++) {
					if(!matches[j] && guess.asArray()[i].compareTo(secret.asArray()[j]) == 0){
						matches[j] = true;
						matchesCount++;
						break;
					}
				}
				
			}	
		}
		
		return matchesCount;
	}

}
