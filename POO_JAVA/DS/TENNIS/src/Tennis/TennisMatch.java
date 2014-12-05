package Tennis;
import java.util.ArrayList;
import java.util.List;


public class TennisMatch{
	public enum MatchType{
		BEST_OF_THREE,
		BEST_OF_FIVE
	}
	
	private final Player player1;
	private final Player player2;
	
	private List<Set> sets;
	
	private int setsCounter = -1;
	private Game game;
	private MatchType matchType;
	private boolean tieBreakInLastSet;
	
	private boolean isFinished;
	
	public TennisMatch(Player player1, Player player2, MatchType matchType, boolean tieBreakInLastSet){
		this.player1 = player1;
		this.player2 = player2;
		this.matchType = matchType;
		this.tieBreakInLastSet = tieBreakInLastSet;
		
		
		this.game = new Game(player1, player2);
		
		resetGame();
		
		this.sets = new ArrayList<Set>();
		this.isFinished = nextSet();
	}

	private void resetGame() {
		game.resetGame();
	}
	
	public void updateWithPointWonBy(Player winner){
		if(isFinished){
			return;
		}
		boolean isGameWin = game.addScoreForPlayer(winner);
		if(isGameWin){
			System.out.println("Game win !");
			gameWinForPlayer(winner);
		}
	}
	
	private void gameWinForPlayer(Player winner){
		sets.get(setsCounter).addAGameForPlayer(winner);
		
		resetGame();
		
		if(isLastSet() && tieBreakInLastSet){
			// TODO : Verify if isLastSet and tieBreakInLastSet
			if(gamesInCurrentSetForPlayer(winner) >= 3){
				isFinished = nextSet();
			}
		} else {
			// TODO : Verify if set is over or not
			if(gamesInCurrentSetForPlayer(winner) >= 3){
				isFinished = nextSet();
			}
		}
	}


	private boolean nextSet() {
		if(!isFinished && !isLastSet()){
			Set set = new Set(player1, player2);
			sets.add(set);
			setsCounter++;
			return false;
		} else {
			return true;
		}
	}
	
	private boolean isLastSet() {
		int nbSetMax;
		switch (matchType) {
		case BEST_OF_FIVE:
			nbSetMax = 5;
			break;
		case BEST_OF_THREE:
			nbSetMax = 3;
			break;
		default:
			nbSetMax = 0;
			break;
		}
		return (currentSetNumber() >= nbSetMax);
	}

	public String pointsForPlayer(Player player){
		return game.getPointsForPlayer(player);
		
	}
	
	public int currentSetNumber(){
		return setsCounter+1;
	}
	
	public int gamesInCurrentSetForPlayer(Player player){
		return gamesInCurrentSetForPlayer(currentSetNumber(), player);
	}
	
	public int gamesInCurrentSetForPlayer(int setId, Player player){
		if(player != player1 && player != player2){
			return -1;
		}
		
		setId -= 1;
		if(setId > setsCounter){
			return -1;
		}
		
		Set set = sets.get(setId);
		return set.getGamesInSetForPlayer(player);
	}
	
	public boolean isFinished(){
		return isFinished;
	}
}
