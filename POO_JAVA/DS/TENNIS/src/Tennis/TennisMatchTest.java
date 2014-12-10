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
	
	public void noPlayerWin(){
		match.updateWithPointWonBy(null);
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
	public void player1WinTheFirstGame(){
		playerWinAGame(player1);
		assertEquals(1, match.gamesInCurrentSetForPlayer(player1));
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
		assertEquals(1, match.gamesInCurrentSetForPlayer(player1));
	}
	
	@Test
	public void player1WinTheFirstSet(){
		playerWinASet(player1);
		assertEquals(6, match.gamesInSetForPlayer(1, player1));
		assertEquals(0, match.gamesInCurrentSetForPlayer(player1));
		assertEquals(0, match.gamesInSetForPlayer(2, player1));
		assertEquals(0, match.gamesInSetForPlayer(1, player2));
		assertEquals(0, match.gamesInCurrentSetForPlayer(player2));
		assertEquals(0, match.gamesInSetForPlayer(2, player1));
	}
	
	@Test
	public void player1WinTheMatchInBO3(){
		playerWinASet(player1);
		playerWinASet(player1);
		assertEquals(6, match.gamesInSetForPlayer(1, player1));
		assertEquals(6, match.gamesInSetForPlayer(2, player1));
		assertEquals(0, match.gamesInSetForPlayer(1, player2));
		assertEquals(0, match.gamesInSetForPlayer(2, player2));
	}
	
	@Test
	public void player1Win2SetsAndPlayer2Win1SetInBO3(){
		playerWinASet(player1);
		playerWinASet(player2);
		playerWinASet(player1);
		assertEquals(6, match.gamesInSetForPlayer(1, player1));
		assertEquals(0, match.gamesInSetForPlayer(1, player2));
		assertEquals(0, match.gamesInSetForPlayer(2, player1));
		assertEquals(6, match.gamesInSetForPlayer(2, player2));
		assertEquals(6, match.gamesInSetForPlayer(3, player1));
		assertEquals(0, match.gamesInSetForPlayer(3, player2));
	}
	
	@Test
	public void player1Win4SetsInBO3(){
		playerWinASet(player1);
		playerWinASet(player1);
		playerWinASet(player1);
		playerWinASet(player1);
		assertEquals(6, match.gamesInSetForPlayer(1, player1));
		assertEquals(6, match.gamesInSetForPlayer(2, player1));
		assertEquals(-1, match.gamesInSetForPlayer(3, player1));
		assertEquals(-1, match.gamesInSetForPlayer(4, player1));
	}
	
	// TODO : Test pour le cas du TieBreak : 6/6 dans un set
	// TODO : Test pour le cas d'un BO5 : 3 sets pour p1
	// TODO : Test pour le cas d'un BO5 : 3 sets pour p1 et 2 sets pour p2
	// TODO : Test pour le cas du dernier set avec TieBreak : 6/6 dans dernier set
	// TODO : Test pour le cas du dernier set sans TieBreak : 6/6 dans dernier set

	public void playerWinAGame(Player p){
		match.updateWithPointWonBy(p);
		match.updateWithPointWonBy(p);
		match.updateWithPointWonBy(p);
		match.updateWithPointWonBy(p);
	}
	
	public void playerWinASet(Player p){
		playerWinAGame(p);
		playerWinAGame(p);
		playerWinAGame(p);
		playerWinAGame(p);
		playerWinAGame(p);
		playerWinAGame(p);
	}
}
