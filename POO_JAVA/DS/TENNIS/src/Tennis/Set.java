package Tennis;

import java.util.HashMap;
import java.util.Map;

public class Set {

	private Map<Player, Integer> set;
	
	protected Player player1;
	protected Player player2;
	
	private AGame game;
	
	//R Constructor
	public Set(Player player1, Player player2, boolean isTimeBreak) {
		this.player1 = player1;
		this.player2 = player2;
		
		set = new HashMap<Player, Integer>();
		set.put(player1, 0);
		set.put(player2, 0);
		
		resetGame(isTimeBreak);
	}
	//ER

	//R Set points Management
	public void addSetPointForPlayer(Player player) {
		set.put(player, getGamesInSetForPlayer(player)+1);
		
		game.resetGame();
	}
	
	public int getGamesInSetForPlayer(Player player){
		return set.get(player);
	}
	//ER
	
	//R Game Management
	private void resetGame(boolean isTimeBreak) {
		if(isTimeBreak)
			this.game = new TieBreak(player1, player2);
		else
			this.game = new Game(player1, player2);
		
		game.resetGame();
	}
	
	public boolean addGamePointForPlayer(Player winner){
		boolean isGameFinished = false;
		
		isGameFinished = game.addScoreForPlayer(winner);
		if(isGameFinished){
			addSetPointForPlayer(winner);
			Player otherPlayer = (winner.equals(player1) ? player2 : player1);
			int scorePlayerWinner = getGamesInSetForPlayer(winner);
			int scoreOtherPlayer = getGamesInSetForPlayer(otherPlayer);
			if(scorePlayerWinner == 6 && scoreOtherPlayer == 6){
				resetGame(true);
			} else if(scorePlayerWinner >= 6 && scorePlayerWinner - scorePlayerWinner >= 2){
				isGameFinished = true;
			} else {
				resetGame(false);
			}
		}
		
		return isGameFinished;
	}
	
	public String getGamePointsForPlayer(Player player){
		return game.getPointsForPlayer(player);
	}
	//ER
	
	
}