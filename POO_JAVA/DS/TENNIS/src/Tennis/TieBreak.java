package Tennis;


public class TieBreak extends AGame {
	
	public TieBreak(Player player1, Player player2) {
		super(player1, player2);
	}

	
	@Override
	public boolean addScoreForPlayer(Player player){
		boolean isGameFinished = false;
		String previousScore = this.getPointsForPlayer(player);
		Player otherPlayer = (player.equals(player1) ? player2 : player1);
		
		String actualScore = Integer.toString(Integer.parseInt(previousScore)+1);
		game.put(player, actualScore);
		
		// Verification of the end of the game
		int actualScoreInt = Integer.parseInt(actualScore);
		int otherPlayerScoreInt = Integer.parseInt(game.get(otherPlayer));
		// Score >= 7
		// 2 points difference
		if(actualScoreInt >= 7 && actualScoreInt - otherPlayerScoreInt >= 2){
			isGameFinished = true;
		}
		
		return isGameFinished;
	}
}