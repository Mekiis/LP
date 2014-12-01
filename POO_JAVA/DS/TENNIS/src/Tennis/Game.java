package Tennis;

import java.util.HashMap;
import java.util.Map;

public class Game {
	private Map<Player, String> game;
	
	private Player player1;
	private Player player2;

	public Game(Player player1, Player player2) {
		this.game = new HashMap<Player, String>();
		
		this.player1 = player1;
		this.player2 = player2;
		
		resetGame();
	}

	public void resetGame() {
		this.game.put(player1, "0");
		this.game.put(player2, "0");
	}
	
	public String getScoreForPlayer(Player player){
		return this.game.get(player);
	}
	
	public boolean addScoreForPlayer(Player player){
		String previousScore = this.getScoreForPlayer(player);
		Player otherPlayer = (player.equals(player1) ? player2 : player1);
				
		switch (previousScore) {
		case "0":
			game.put(player, "15");
			break;
		case "15":
			game.put(player, "30");
			break;
		case "30":
			switch (this.getScoreForPlayer(otherPlayer)) {
			case "30":
				game.put(player, "A");
				break;
			case "A":
				game.put(otherPlayer, "30");
				break;
			default:
				return true;
			}
			break;
		}
				
		return false;
	}
	
	public String getPointsForPlayer(Player player){
		return game.get(player);
	}
}