package Tennis;

import java.util.HashMap;
import java.util.Map;


public abstract class AGame {
	protected Map<Player, String> game;
	
	protected Player player1;
	protected Player player2;

	public AGame(Player player1, Player player2) {
		this.game = new HashMap<Player, String>();
		
		this.player1 = player1;
		this.player2 = player2;
		
		resetGame();
	}

	public void resetGame() {
		this.game.put(player1, "0");
		this.game.put(player2, "0");
	}

	public abstract boolean addScoreForPlayer(Player player);

	public String getPointsForPlayer(Player player){
		return game.get(player);
	}

}