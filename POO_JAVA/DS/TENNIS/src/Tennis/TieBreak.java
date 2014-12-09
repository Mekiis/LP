package Tennis;

import java.util.HashMap;
import java.util.Map;

public class TieBreak implements AGame {
	private Map<Player, String> game;
	
	private Player player1;
	private Player player2;

	public TieBreak(Player player1, Player player2) {
		this.game = new HashMap<Player, String>();
		
		this.player1 = player1;
		this.player2 = player2;
		
		resetGame();
	}

	@Override
	public void resetGame() {
		this.game.put(player1, "0");
		this.game.put(player2, "0");
	}
	
	
	@Override
	public boolean addScoreForPlayer(Player player){
		String previousScore = this.getPointsForPlayer(player);
		Player otherPlayer = (player.equals(player1) ? player2 : player1);
		
				
		return false;
	}
	
	@Override
	public String getPointsForPlayer(Player player){
		return game.get(player);
	}
}