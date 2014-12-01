package Tennis;

import static org.junit.Assert.*;

import org.junit.Test;

public class TennisMatchTest {
	
	static final Player player1 = new Player("Bob");
	static final Player player2 = new Player("Lea");
	static TennisMatch match;
	
	@Test
	public void player1AndPlayer2AreNotEquals() {
		assertFalse(player1.equals(player2));
	}
	
	@Test
	public void initialSetPointForPlayers(){
		match = new TennisMatch(player1, player2, TennisMatch.MatchType.BEST_OF_FIVE, false);
		assertEquals("0", match.pointsForPlayer(player1));
		assertEquals("0", match.pointsForPlayer(player2));
	}
	
	@Test
	public void player1WinTheFirstPoint(){
		match.updateWithPointWonBy(player1);
		assertEquals("15", match.pointsForPlayer(player1));
	}
	
	@Test
	public void player1WinTheSecondPoint(){
		match.updateWithPointWonBy(player1);
		assertEquals("30", match.pointsForPlayer(player1));
	}
	
	@Test
	public void player1WinTheFirstSet(){
		match.updateWithPointWonBy(player1);
		assertEquals(1, match.gamesInCurrentSetForPlayer(player1));
	}
	
	@Test
	public void player2WinTheFirstPoint(){
		match.updateWithPointWonBy(player2);
		assertEquals("15", match.pointsForPlayer(player2));
	}
	
	@Test
	public void player2WinTheSecondPoint(){
		match.updateWithPointWonBy(player2);
		assertEquals("30", match.pointsForPlayer(player2));
	}
	
	@Test
	public void player2WinTheSecondSet(){
		match.updateWithPointWonBy(player2);
		assertEquals(1, match.gamesInCurrentSetForPlayer(player2));
	}
	
	@Test
	public void player2EqualPlayer1WhenPlayer1Have30Point(){
		match.updateWithPointWonBy(player1);
		match.updateWithPointWonBy(player1);
		match.updateWithPointWonBy(player2);
		match.updateWithPointWonBy(player2);
		assertEquals("30", match.pointsForPlayer(player1));
		assertEquals("30", match.pointsForPlayer(player2));
	}
	
	@Test
	public void player2HaveTeAdvantage(){
		match.updateWithPointWonBy(player2);
		assertEquals("A", match.pointsForPlayer(player2));
	}
	
	@Test
	public void player2WinTheThirdPoint(){
		match.updateWithPointWonBy(player2);
		assertEquals(2, match.gamesInCurrentSetForPlayer(player2));
	}

}
