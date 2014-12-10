package Tennis;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TennisMatchTest {
	
	static final Player player1 = new Player("Bob");
	static final Player player2 = new Player("Lea");
	static TennisMatch match;
	
	@Before
	public void initMatch(){
		match = new TennisMatch(player1, player2, TennisMatch.MatchType.BEST_OF_THREE, false);
	}
	
	@Test
	public void player1AndPlayer2AreNotEquals() {
		assertFalse(player1.equals(player2));
	}
	
	@Test
	public void player1WinTheFirstPoint(){
		match.updateWithPointWonBy(player1);
		assertEquals("15", match.pointsForPlayer(player1));
	}
	
	@Test
	public void player1WinTheSecondPoint(){
		match.updateWithPointWonBy(player1);
		match.updateWithPointWonBy(player1);
		assertEquals("30", match.pointsForPlayer(player1));
	}
	
	@Test
	public void player1WinTheThirdPoint(){
		match.updateWithPointWonBy(player1);
		match.updateWithPointWonBy(player1);
		match.updateWithPointWonBy(player1);
		assertEquals("40", match.pointsForPlayer(player1));
	}
	
	@Test
	public void player1WinTheFirstSet(){
		playerWinTheGame(player1);
		assertEquals(1, match.gamesInCurrentSetForPlayer(player1));
	}
	
	@Test
	public void player1AndPlayer2Have40Points(){
		match.updateWithPointWonBy(player1);
		match.updateWithPointWonBy(player1);
		match.updateWithPointWonBy(player1);
		match.updateWithPointWonBy(player2);
		match.updateWithPointWonBy(player2);
		match.updateWithPointWonBy(player2);
		assertEquals("40", match.pointsForPlayer(player1));
		assertEquals("40", match.pointsForPlayer(player2));
	}
	
	@Test
	public void player1TakeTheAdvantage(){
		match.updateWithPointWonBy(player1);
		match.updateWithPointWonBy(player1);
		match.updateWithPointWonBy(player1);
		match.updateWithPointWonBy(player2);
		match.updateWithPointWonBy(player2);
		match.updateWithPointWonBy(player2);
		match.updateWithPointWonBy(player1);
		assertEquals("A", match.pointsForPlayer(player1));
		assertEquals("40", match.pointsForPlayer(player2));
	}
	
	@Test
	public void player1TakeTheAdvantageAndWinTheGame(){
		match.updateWithPointWonBy(player1);
		match.updateWithPointWonBy(player1);
		match.updateWithPointWonBy(player1);
		assertEquals("40", match.pointsForPlayer(player1));
		match.updateWithPointWonBy(player2);
		match.updateWithPointWonBy(player2);
		match.updateWithPointWonBy(player2);
		assertEquals("40", match.pointsForPlayer(player2));
		match.updateWithPointWonBy(player1);
		match.updateWithPointWonBy(player1);
		assertEquals(1, match.gamesInCurrentSetForPlayer(1, player1));
	}

	public void playerWinTheGame(Player p){
		match.updateWithPointWonBy(p);
		match.updateWithPointWonBy(p);
		match.updateWithPointWonBy(p);
		match.updateWithPointWonBy(p);
	}
}
