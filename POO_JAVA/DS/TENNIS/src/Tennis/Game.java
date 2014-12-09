package Tennis;

import java.util.HashMap;
import java.util.Map;

public class Game extends AGame {

	public Game(Player player1, Player player2) {
		super(player1, player2);
	}

	@Override
	public boolean addScoreForPlayer(Player player){
		boolean isGameFinished = false;
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
				isGameFinished = true;
			}
		} else if(previousScore.equals("A"))
			isGameFinished = true;
		
				
		return isGameFinished;
	}
}