package fr.univ.lyon1.mastermind;

import java.util.ArrayList;
import java.util.List;
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
		
		List<Integer> removedIndex = new ArrayList<Integer>();
		for(int i = 0;i<secret.length();i++){
			for(int j = 0;j<guess.length();j++){
				if(guess.asArray()[j] == secret.asArray()[i] && !(removedIndex.contains(i))){
					matchesCount++;
					removedIndex.add(i);
				}
			}
		}
		return matchesCount;
	}

}
