package Tennis;


public interface AGame {

	public abstract void resetGame();

	public abstract boolean addScoreForPlayer(Player player);

	public abstract String getPointsForPlayer(Player player);

}