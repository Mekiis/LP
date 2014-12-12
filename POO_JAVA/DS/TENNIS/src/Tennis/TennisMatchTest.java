package Tennis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

public class TennisMatchTest {
	
	static final Player player1 = new Player("Bob");
	static final Player player2 = new Player("Lea");
	static final Player player3 = new Player("Foo");
	static TennisMatch matchBO3;
	static TennisMatch matchBO5;
	static TennisMatch matchBO3NoTieBreak;
	static TennisMatch matchBO3TieBreak;
	
	@Before
	public void initMatch(){
		matchBO3 = new TennisMatch(player1, player2, TennisMatch.MatchType.BEST_OF_THREE, true);
		matchBO5 = new TennisMatch(player1, player2, TennisMatch.MatchType.BEST_OF_FIVE, true);
		matchBO3NoTieBreak = new TennisMatch(player1, player2, TennisMatch.MatchType.BEST_OF_THREE, false);
		matchBO3TieBreak = new TennisMatch(player1, player2, TennisMatch.MatchType.BEST_OF_THREE, true);
	}
	
	@Test
	public void player1AndPlayer2AreNotEquals() {
		assertFalse(player1.equals(player2));
	}
	
	public void noPlayerWin(){
		matchBO3.updateWithPointWonBy(null);
		
		assertEquals("0", matchBO3.pointsForPlayer(player1));
		assertEquals("0", matchBO3.pointsForPlayer(player2));
	}
	
	@Test
	public void player1WinTheFirstPoint(){
		matchBO3.updateWithPointWonBy(player1);
		
		assertEquals("15", matchBO3.pointsForPlayer(player1));
	}
	
	@Test
	public void player1WinTheSecondPoint(){
		matchBO3.updateWithPointWonBy(player1);
		matchBO3.updateWithPointWonBy(player1);
		
		assertEquals("30", matchBO3.pointsForPlayer(player1));
	}
	
	@Test
	public void player1WinTheThirdPoint(){
		matchBO3.updateWithPointWonBy(player1);
		matchBO3.updateWithPointWonBy(player1);
		matchBO3.updateWithPointWonBy(player1);
		
		assertEquals("40", matchBO3.pointsForPlayer(player1));
	}
	
	@Test
	public void player1AndPlayer2Have40Points(){
		matchBO3.updateWithPointWonBy(player1);
		matchBO3.updateWithPointWonBy(player1);
		matchBO3.updateWithPointWonBy(player1);
		
		matchBO3.updateWithPointWonBy(player2);
		matchBO3.updateWithPointWonBy(player2);
		matchBO3.updateWithPointWonBy(player2);
		
		assertEquals("40", matchBO3.pointsForPlayer(player1));
		assertEquals("40", matchBO3.pointsForPlayer(player2));
	}
	
	@Test
	public void playerNotInMatchWinAPoint(){
		matchBO3.updateWithPointWonBy(player3);
		
		assertEquals("0", matchBO3.pointsForPlayer(player1));
		assertEquals("0", matchBO3.pointsForPlayer(player2));
	}
	
	@Test
	public void player1TakeTheAdvantage(){
		matchBO3.updateWithPointWonBy(player1);
		matchBO3.updateWithPointWonBy(player1);
		matchBO3.updateWithPointWonBy(player1);
		
		matchBO3.updateWithPointWonBy(player2);
		matchBO3.updateWithPointWonBy(player2);
		matchBO3.updateWithPointWonBy(player2);
		
		matchBO3.updateWithPointWonBy(player1);
		
		assertEquals("A", matchBO3.pointsForPlayer(player1));
		assertEquals("40", matchBO3.pointsForPlayer(player2));
	}
	
	@Test
	public void player1WinTheFirstGame(){
		playerWinAGame(player1, matchBO3);
		
		assertEquals(1, matchBO3.gamesInCurrentSetForPlayer(player1));
	}
	
	@Test
	public void player1TakeTheAdvantageAndWinTheGame(){
		matchBO3.updateWithPointWonBy(player1);
		matchBO3.updateWithPointWonBy(player1);
		matchBO3.updateWithPointWonBy(player1);
		
		assertEquals("40", matchBO3.pointsForPlayer(player1));
		
		matchBO3.updateWithPointWonBy(player2);
		matchBO3.updateWithPointWonBy(player2);
		matchBO3.updateWithPointWonBy(player2);
		
		assertEquals("40", matchBO3.pointsForPlayer(player2));
		
		matchBO3.updateWithPointWonBy(player1);
		matchBO3.updateWithPointWonBy(player1);
		
		assertEquals(1, matchBO3.gamesInCurrentSetForPlayer(player1));
	}
	
	@Test
	public void player1WinTheFirstSet(){
		playerWinASet(player1, matchBO3);
		
		assertEquals(6, matchBO3.gamesInSetForPlayer(1, player1));
		assertEquals(0, matchBO3.gamesInSetForPlayer(1, player2));
		
		assertEquals(0, matchBO3.gamesInCurrentSetForPlayer(player1));
		assertEquals(0, matchBO3.gamesInCurrentSetForPlayer(player2));
		
		assertEquals(0, matchBO3.gamesInSetForPlayer(2, player1));
		assertEquals(0, matchBO3.gamesInSetForPlayer(2, player2));
	}
	
	@Test
	public void player1WinTheMatchInBO3(){
		playerWinASet(player1, matchBO3);
		playerWinASet(player1, matchBO3);
		
		assertEquals(6, matchBO3.gamesInSetForPlayer(1, player1));
		assertEquals(0, matchBO3.gamesInSetForPlayer(1, player2));
		
		assertEquals(6, matchBO3.gamesInSetForPlayer(2, player1));
		assertEquals(0, matchBO3.gamesInSetForPlayer(2, player2));
	}
	
	@Test
	public void player1Win2SetsAndPlayer2Win1SetInBO3(){
		playerWinASet(player1, matchBO3);
		playerWinASet(player2, matchBO3);
		playerWinASet(player1, matchBO3);
		
		assertEquals(6, matchBO3.gamesInSetForPlayer(1, player1));
		assertEquals(0, matchBO3.gamesInSetForPlayer(1, player2));
		
		assertEquals(0, matchBO3.gamesInSetForPlayer(2, player1));
		assertEquals(6, matchBO3.gamesInSetForPlayer(2, player2));
		
		assertEquals(6, matchBO3.gamesInSetForPlayer(3, player1));
		assertEquals(0, matchBO3.gamesInSetForPlayer(3, player2));
	}
	
	@Test
	public void player1Win4SetsInBO3(){
		playerWinASet(player1, matchBO3);
		playerWinASet(player1, matchBO3);
		playerWinASet(player1, matchBO3);
		playerWinASet(player1, matchBO3);
		
		assertEquals(6, matchBO3.gamesInSetForPlayer(1, player1));
		assertEquals(6, matchBO3.gamesInSetForPlayer(2, player1));
		assertEquals(-1, matchBO3.gamesInSetForPlayer(3, player1));
		assertEquals(-1, matchBO3.gamesInSetForPlayer(4, player1));
	}
	
	@Test
	public void tieBreakInASet(){
		playerWinAGame(player1, matchBO3);
		playerWinAGame(player1, matchBO3);
		playerWinAGame(player1, matchBO3);
		playerWinAGame(player1, matchBO3);
		playerWinAGame(player1, matchBO3);
		
		playerWinAGame(player2, matchBO3);
		playerWinAGame(player2, matchBO3);
		playerWinAGame(player2, matchBO3);
		playerWinAGame(player2, matchBO3);
		playerWinAGame(player2, matchBO3);
		playerWinAGame(player2, matchBO3);
		
		playerWinAGame(player1, matchBO3);
		
		assertEquals(6, matchBO3.gamesInCurrentSetForPlayer(player1));
		assertEquals(6, matchBO3.gamesInCurrentSetForPlayer(player2));
		
		matchBO3.updateWithPointWonBy(player1);
		matchBO3.updateWithPointWonBy(player1);
		matchBO3.updateWithPointWonBy(player1);
		matchBO3.updateWithPointWonBy(player1);
		matchBO3.updateWithPointWonBy(player1);
		matchBO3.updateWithPointWonBy(player1);
		
		matchBO3.updateWithPointWonBy(player2);
		matchBO3.updateWithPointWonBy(player2);
		matchBO3.updateWithPointWonBy(player2);
		matchBO3.updateWithPointWonBy(player2);
		matchBO3.updateWithPointWonBy(player2);
		matchBO3.updateWithPointWonBy(player2);
		matchBO3.updateWithPointWonBy(player2);
		
		matchBO3.updateWithPointWonBy(player1);
		matchBO3.updateWithPointWonBy(player1);
		
		assertEquals("8", matchBO3.pointsForPlayer(player1));
		assertEquals("7", matchBO3.pointsForPlayer(player2));
		
		matchBO3.updateWithPointWonBy(player1);
		
		assertEquals(7, matchBO3.gamesInSetForPlayer(1, player1));
		assertEquals(6, matchBO3.gamesInSetForPlayer(1, player2));
	}
	
	@Test
	public void player1WinTheMatchInBO5(){
		playerWinASet(player1, matchBO5);
		playerWinASet(player1, matchBO5);
		playerWinASet(player1, matchBO5);
		
		assertEquals(6, matchBO5.gamesInSetForPlayer(1, player1));
		assertEquals(0, matchBO5.gamesInSetForPlayer(1, player2));
		
		assertEquals(6, matchBO5.gamesInSetForPlayer(2, player1));
		assertEquals(0, matchBO5.gamesInSetForPlayer(2, player2));
		
		assertEquals(6, matchBO5.gamesInSetForPlayer(3, player1));
		assertEquals(0, matchBO5.gamesInSetForPlayer(3, player2));
	}
	
	@Test
	public void player1Win6SetsInBO5(){
		playerWinASet(player1, matchBO5);
		playerWinASet(player1, matchBO5);
		playerWinASet(player1, matchBO5);
		playerWinASet(player1, matchBO5);
		playerWinASet(player1, matchBO5);
		playerWinASet(player1, matchBO5);
		
		assertEquals(6, matchBO5.gamesInSetForPlayer(1, player1));
		assertEquals(0, matchBO5.gamesInSetForPlayer(1, player2));
		
		assertEquals(6, matchBO5.gamesInSetForPlayer(2, player1));
		assertEquals(0, matchBO5.gamesInSetForPlayer(2, player2));
		
		assertEquals(6, matchBO5.gamesInSetForPlayer(3, player1));
		assertEquals(0, matchBO5.gamesInSetForPlayer(3, player2));
		
		assertEquals(-1, matchBO5.gamesInSetForPlayer(4, player1));
		assertEquals(-1, matchBO5.gamesInSetForPlayer(4, player2));
		
		assertEquals(-1, matchBO5.gamesInSetForPlayer(5, player1));
		assertEquals(-1, matchBO5.gamesInSetForPlayer(5, player2));
		
		assertEquals(-1, matchBO5.gamesInSetForPlayer(6, player1));
		assertEquals(-1, matchBO5.gamesInSetForPlayer(6, player2));
	}
	
	@Test
	public void player1Win3SetsAndPlayer2Win2SetInBO5(){
		playerWinASet(player1, matchBO5);
		playerWinASet(player2, matchBO5);
		playerWinASet(player2, matchBO5);
		playerWinASet(player1, matchBO5);
		playerWinASet(player1, matchBO5);
		
		assertEquals(6, matchBO5.gamesInSetForPlayer(1, player1));
		assertEquals(0, matchBO5.gamesInSetForPlayer(1, player2));
		
		assertEquals(0, matchBO5.gamesInSetForPlayer(2, player1));
		assertEquals(6, matchBO5.gamesInSetForPlayer(2, player2));
		
		assertEquals(0, matchBO5.gamesInSetForPlayer(3, player1));
		assertEquals(6, matchBO5.gamesInSetForPlayer(3, player2));
		
		assertEquals(6, matchBO5.gamesInSetForPlayer(4, player1));
		assertEquals(0, matchBO5.gamesInSetForPlayer(4, player2));
		
		assertEquals(6, matchBO5.gamesInSetForPlayer(5, player1));
		assertEquals(0, matchBO5.gamesInSetForPlayer(5, player2));
	}
	
	@Test
	public void tieBreakInLastSetThenPlayer2WinTheMatch(){
		playerWinASet(player1, matchBO3TieBreak);
		playerWinASet(player2, matchBO3TieBreak);
		
		playerWinAGame(player1, matchBO3TieBreak);
		playerWinAGame(player1, matchBO3TieBreak);
		playerWinAGame(player1, matchBO3TieBreak);
		playerWinAGame(player1, matchBO3TieBreak);
		playerWinAGame(player1, matchBO3TieBreak);
		
		playerWinAGame(player2, matchBO3TieBreak);
		playerWinAGame(player2, matchBO3TieBreak);
		playerWinAGame(player2, matchBO3TieBreak);
		playerWinAGame(player2, matchBO3TieBreak);
		playerWinAGame(player2, matchBO3TieBreak);
		
		playerWinAGame(player1, matchBO3TieBreak);
		playerWinAGame(player2, matchBO3TieBreak);
		
		matchBO3TieBreak.updateWithPointWonBy(player1);
		matchBO3TieBreak.updateWithPointWonBy(player1);
		matchBO3TieBreak.updateWithPointWonBy(player1);
		matchBO3TieBreak.updateWithPointWonBy(player1);
		
		matchBO3TieBreak.updateWithPointWonBy(player2);
		matchBO3TieBreak.updateWithPointWonBy(player2);
		matchBO3TieBreak.updateWithPointWonBy(player2);
		matchBO3TieBreak.updateWithPointWonBy(player2);
		matchBO3TieBreak.updateWithPointWonBy(player2);
		matchBO3TieBreak.updateWithPointWonBy(player2);
		
		assertEquals("4", matchBO3TieBreak.pointsForPlayer(player1));
		assertEquals("6", matchBO3TieBreak.pointsForPlayer(player2));
		
		matchBO3TieBreak.updateWithPointWonBy(player2);
		
		assertEquals(6, matchBO3TieBreak.gamesInSetForPlayer(3, player1));
		assertEquals(7, matchBO3TieBreak.gamesInSetForPlayer(3, player2));
	}
	
	@Test
	public void noTieBreakInLastSetThenPlayer2WinTheMatch(){
		playerWinASet(player1, matchBO3NoTieBreak);
		playerWinASet(player2, matchBO3NoTieBreak);
		
		playerWinAGame(player1, matchBO3NoTieBreak);
		playerWinAGame(player1, matchBO3NoTieBreak);
		playerWinAGame(player1, matchBO3NoTieBreak);
		playerWinAGame(player1, matchBO3NoTieBreak);
		playerWinAGame(player1, matchBO3NoTieBreak);
		
		playerWinAGame(player2, matchBO3NoTieBreak);
		playerWinAGame(player2, matchBO3NoTieBreak);
		playerWinAGame(player2, matchBO3NoTieBreak);
		playerWinAGame(player2, matchBO3NoTieBreak);
		playerWinAGame(player2, matchBO3NoTieBreak);
		
		playerWinAGame(player1, matchBO3NoTieBreak);
		playerWinAGame(player2, matchBO3NoTieBreak);
		
		matchBO3NoTieBreak.updateWithPointWonBy(player1);
		matchBO3NoTieBreak.updateWithPointWonBy(player1);
		matchBO3NoTieBreak.updateWithPointWonBy(player1);
		
		matchBO3NoTieBreak.updateWithPointWonBy(player2);
		matchBO3NoTieBreak.updateWithPointWonBy(player2);
		matchBO3NoTieBreak.updateWithPointWonBy(player2);
		
		assertEquals("40", matchBO3NoTieBreak.pointsForPlayer(player1));
		assertEquals("40", matchBO3NoTieBreak.pointsForPlayer(player2));
		
		matchBO3NoTieBreak.updateWithPointWonBy(player2);
		matchBO3NoTieBreak.updateWithPointWonBy(player2);
		
		assertEquals(6, matchBO3NoTieBreak.gamesInSetForPlayer(3, player1));
		assertEquals(7, matchBO3NoTieBreak.gamesInSetForPlayer(3, player2));
	}
	
	public void playerWinAGame(Player p, TennisMatch m){
		m.updateWithPointWonBy(p);
		m.updateWithPointWonBy(p);
		m.updateWithPointWonBy(p);
		m.updateWithPointWonBy(p);
	}
	
	public void playerWinASet(Player p, TennisMatch m){
		playerWinAGame(p, m);
		playerWinAGame(p, m);
		playerWinAGame(p, m);
		playerWinAGame(p, m);
		playerWinAGame(p, m);
		playerWinAGame(p, m);
	}
}
