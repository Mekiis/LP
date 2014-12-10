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
	private Set actualSet;
	private int setsCounter = -1;
	
	private MatchType matchType;
	private boolean tieBreakInLastSet;
	
	private boolean isFinished;
	
	//R Constructor
	public TennisMatch(Player player1, Player player2, MatchType matchType, boolean tieBreakInLastSet){
		this.player1 = player1;
		this.player2 = player2;
		
		this.matchType = matchType;
		
		this.tieBreakInLastSet = tieBreakInLastSet;
		
		this.sets = new ArrayList<Set>();
		this.isFinished = nextSet();
	}
	//ER

	public void updateWithPointWonBy(Player winner){
		if(isFinished){
			return;
		}
		
		boolean isSetWin = actualSet.addGamePointForPlayer(winner);
		if(isSetWin){
			isFinished = nextSet();
		}
	}

	/***
	 * Create if needed the next set, increment the counter and have a reference on the new set
	 * @return <i>boolean</i> if the match is finished, there is no set to play. It return false. Else it return true
	 */
	private boolean nextSet() {
		if(!isFinished && isNeededOneMoreSet()){
			Set set = new Set(player1, player2, false);
			sets.add(set);
			setsCounter++;
			actualSet = sets.get(setsCounter);
			return false;
		} else {
			return true;
		}
	}
	
	private boolean isNeededOneMoreSet() {
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
		return (currentSetNumber() < nbSetMax);
	}

	public String pointsForPlayer(Player player){
		return sets.get(setsCounter).getGamePointsForPlayer(player);
		
	}
	
	public int currentSetNumber(){
		return setsCounter+1;
	}
	
	public int gamesInCurrentSetForPlayer(Player player){
		if(player != player1 && player != player2){
			return -1;
		}
		
		return gamesInCurrentSetForPlayer(currentSetNumber(), player);
	}
	
	public int gamesInCurrentSetForPlayer(int setId, Player player){
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
