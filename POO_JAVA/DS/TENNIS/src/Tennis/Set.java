package Tennis;

import java.util.HashMap;
import java.util.Map;

public class Set {

	private Map<Player, Integer> set;
	
	public Set(Player player1, Player player2) {
		set = new HashMap<Player, Integer>();
		set.put(player1, 0);
		set.put(player2, 0);
	}

	public void addAGameForPlayer(Player player) {
		set.put(player, getGamesInSetForPlayer(player)+1);
	}
	
	public int getGamesInSetForPlayer(Player player){
		return set.get(player);
	}
}