package Tennis;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
		
		if(winner != player1 && winner != player2){
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
	
	/**
	 * Return a boolean to know if players need to play an other set or if one player have win the match (Can vary with MatchType (BO3/BO5) value)
	 * @return <i>boolean</i> if one player has the minimum of set to win, it return false. Else, they need to play, it return true. If no type of match is specified, the match is automatically finished (0 set to win)
	 */
	private boolean isNeededOneMoreSet() {
		boolean isNeededOneMoreSet = true;
		
		Map<Player, Integer> setsWin = new HashMap<>();
		for(Set s : sets){
			if(s.isWin()){
				if(setsWin.containsKey(s.getWinner())){
					setsWin.put(s.getWinner(), setsWin.get(s.getWinner())+1);
				} else {
					setsWin.put(s.getWinner(), 1);
				}
			}
		}
		
		int NB_SET_TO_WIN = 0;
		switch (matchType) {
		case BEST_OF_FIVE:
			NB_SET_TO_WIN = 3;
			break;
		case BEST_OF_THREE:
			NB_SET_TO_WIN = 2;
			break;
		}
		
		if(		(setsWin.containsKey(player1) && setsWin.get(player1) >= NB_SET_TO_WIN) 
			|| 	(setsWin.containsKey(player2) && setsWin.get(player2) >= NB_SET_TO_WIN))
			isNeededOneMoreSet = false;
		return isNeededOneMoreSet;
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
		
		return gamesInSetForPlayer(currentSetNumber(), player);
	}
	
	public int gamesInSetForPlayer(int setId, Player player){
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
