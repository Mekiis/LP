package fr.univ.lyon1.mastermind;

import static org.junit.Assert.*;
import static fr.univ.lyon1.mastermind.Peg.*;

import org.junit.Test;

public class ScoreTest {

	@Test
	public void testScorer() {
		Code secret = Code.valueOf(RED,BLUE,GREEN,YELLOW);
		Code guess = Code.valueOf(BLUE,GREEN,YELLOW,RED);
		assertEquals(Score.valueOf(0, 4), new Scorer(secret).score(guess));
		
		secret = Code.valueOf(RED,BLUE,GREEN,YELLOW);
		guess = Code.valueOf(RED,BLUE,GREEN,YELLOW);
		assertEquals(Score.valueOf(4, 0), new Scorer(secret).score(guess));
		
		secret = Code.valueOf(RED,BLUE,GREEN,YELLOW);
		guess = Code.valueOf(RED,GREEN,ORANGE,YELLOW);
		assertEquals(Score.valueOf(2, 1), new Scorer(secret).score(guess));	
		
		secret = Code.valueOf(RED,RED,GREEN,GREEN);
		guess = Code.valueOf(BLUE,BLUE,ORANGE,YELLOW);
		assertEquals(Score.valueOf(0, 0), new Scorer(secret).score(guess));	
	}
	
	@Test
	public void testScorerStatic() {
		Code secret = Code.valueOf(RED,BLUE,GREEN,YELLOW);
		Code guess = Code.valueOf(BLUE,GREEN,YELLOW,RED);
		assertEquals(Score.valueOf(0, 4), Scorer.score(guess,secret));
		
		secret = Code.valueOf(RED,BLUE,GREEN,YELLOW);
		guess = Code.valueOf(RED,BLUE,GREEN,YELLOW);
		assertEquals(Score.valueOf(4, 0), Scorer.score(guess,secret));
		
		secret = Code.valueOf(RED,BLUE,GREEN,YELLOW);
		guess = Code.valueOf(RED,GREEN,ORANGE,YELLOW);
		assertEquals(Score.valueOf(2, 1), Scorer.score(guess,secret));	
		
		secret = Code.valueOf(RED,RED,GREEN,GREEN);
		guess = Code.valueOf(BLUE,BLUE,ORANGE,YELLOW);
		assertEquals(Score.valueOf(0, 0), Scorer.score(guess,secret));	
	}
	
	@Test
	public void testCache() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				assertTrue(Score.valueOf(i, j) == Score.valueOf(i, j));
				assertEquals(Score.valueOf(i, j), Score.valueOf(i, j));
			}
			
		}
		
	}

}
