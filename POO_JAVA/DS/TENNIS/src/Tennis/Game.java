package Tennis;

import java.util.HashMap;
import java.util.Map;

public class Game implements AGame {
	private Map<Player, String> game;
	
	private Player player1;
	private Player player2;

	public Game(Player player1, Player player2) {
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
				
		if(previousScore.equals("0"))
			game.put(player, "15");
		else if(previousScore.equals("15"))
			game.put(player, "30");
		else if(previousScore.equals("30"))
			game.put(player, "40");
		else if(previousScore.equals("40")){
			if(this.getPointsForPlayer(otherPlayer).equals("40")){
				game.put(player, "A");
			} else if(this.getPointsForPlayer(otherPlayer).equals("A")){
				game.put(otherPlayer, "30");
			} else {
				return true;
			}
		} else if(previousScore.equals("A"))
			return true;
		
				
		return false;
	}
	
	@Override
	public String getPointsForPlayer(Player player){
		return game.get(player);
	}
}