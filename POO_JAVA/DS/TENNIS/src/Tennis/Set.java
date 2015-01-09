package Tennis;

import java.util.HashMap;
import java.util.Map;

public class Set {

	private Map<Player, Integer> set;
	
	private Player player1;
	private Player player2;
	
	private AGame game;
	
	private boolean isWin = false;
	private Player winner = null;
	
	private boolean isTieBreak = true;
	
	//R Constructor
	public Set(Player player1, Player player2, boolean isTieBreak) {
		this.player1 = player1;
		this.player2 = player2;
		
		set = new HashMap<Player, Integer>();
		set.put(player1, 0);
		set.put(player2, 0);
		
		this.isTieBreak = isTieBreak;
		resetGame(Game.class);
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
	private void resetGame(Class<?> typeOfGame) {
		if(typeOfGame == TieBreak.class)
			this.game = new TieBreak(player1, player2);
		else
			this.game = new Game(player1, player2);
		
		game.resetGame();
	}
	
	public boolean addGamePointForPlayer(Player winner){
		boolean isSetFinished = false;
		
		boolean isGameFinished = game.addScoreForPlayer(winner);
		if(isGameFinished){
			addSetPointForPlayer(winner);
			Player otherPlayer = (winner.equals(player1) ? player2 : player1);
			int scorePlayerWinner = getGamesInSetForPlayer(winner);
			int scoreOtherPlayer = getGamesInSetForPlayer(otherPlayer);
			if((scorePlayerWinner == 6 && scoreOtherPlayer == 6) && isTieBreak){
				resetGame(TieBreak.class);
			} else if((scorePlayerWinner >= 6 && scorePlayerWinner - scoreOtherPlayer >= 2) || (isTieBreak && scorePlayerWinner == 7)){
				this.isWin = true;
				this.winner = winner;
				isSetFinished = true;
			} else {
				resetGame(Game.class);
			}
		}
		
		return isSetFinished;
	}
	
	public String getGamePointsForPlayer(Player player){
		return game.getPointsForPlayer(player);
	}
	//ER

	public boolean isWin() {
		return isWin;
	}

	public Player getWinner() {
		return winner;
	}
}