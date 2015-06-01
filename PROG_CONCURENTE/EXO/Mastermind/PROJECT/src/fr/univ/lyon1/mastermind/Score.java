package fr.univ.lyon1.mastermind;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Score {
	private static ConcurrentMap<Integer,Score> map = new ConcurrentHashMap<>();
	
	private final int whiteCount;
	private final int blackCount;
	
	//Score cache
	// ConcurrentMap<Integer,Score> needs to compute unique key based on the blackCount and whiteCount
	// blackCount and whiteCount belong to [0;code length]
	// key = blackCount * (MAX_CODE_LENGTH * 2) + whiteCount
	
	private Score(int blackCount, int whiteCount) {
		super();
		this.whiteCount = whiteCount;
		this.blackCount = blackCount;
	}

	public int getWhiteCount() {
		return whiteCount;
	}

	public int getBlackCount() {
		return blackCount;
	}
	
	public static Score valueOf(int blackCount, int whiteCount) {
		int key = computeKey(blackCount, whiteCount);
		
		if(!map.containsKey(key)){
			Score s = new Score(blackCount, whiteCount);
			map.put(key, s);
		}
		
		return map.get(key);
	}

	private static Integer computeKey(int blackCount, int whiteCount){
		return blackCount * (Solver.MAX_CODE_LENGTH * 2) + whiteCount;
	}
	
	@Override
	public String toString() {
		return "Score [whites:" + whiteCount + ", blacks:" + blackCount + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + blackCount;
		result = prime * result + whiteCount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return false;
	}

}
