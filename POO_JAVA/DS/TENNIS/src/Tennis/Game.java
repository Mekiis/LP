package Tennis;

public class Game extends AGame {

	public Game(Player player1, Player player2) {
		super(player1, player2);
	}

	@Override
	public boolean addScoreForPlayer(Player player){
		boolean isGameFinished = false;
		Player otherPlayer = (player.equals(player1) ? player2 : player1);
		
		switch (this.getPointsForPlayer(player)){
		case "0":
			game.put(player, "15");
			break;
		case "15":
			game.put(player, "30");
			break;
		case "30":
			game.put(player, "40");
			break;
		case "40":
			switch (this.getPointsForPlayer(otherPlayer)){
			case "40": 
				game.put(player, "A");
				break;
			case "A": 
				game.put(otherPlayer, "40");
				break;
			default:
				isGameFinished = true;
				break;
			}
			break;
		case "A":
			isGameFinished = true;
			break;
		}
		
		return isGameFinished;
	}
}