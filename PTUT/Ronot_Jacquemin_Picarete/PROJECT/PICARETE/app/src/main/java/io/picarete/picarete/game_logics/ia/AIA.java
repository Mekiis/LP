package io.picarete.picarete.game_logics.ia;

import java.util.List;

import io.picarete.picarete.game_logics.Game;
import io.picarete.picarete.game_logics.gameplay.Edge;
import io.picarete.picarete.game_logics.gameplay.Tile;

/**
 * Created by root on 1/7/15.
 */
public abstract class AIA extends Thread {

    public static final double MIN_TIME_TO_FIND = 0.6 * 1000; // In seconds

    protected abstract Edge findEdge(int height, int width, Game game, List<Edge> previousEdgesPlayed);

    public Edge getEdgeFound(int height, int width, Game game, List<Edge> previousEdgePlayed){
        Edge edge = null;

        long timeBefore = System.currentTimeMillis();

        edge = findEdge(height, width, game, previousEdgePlayed);

        long timeAfter = System.currentTimeMillis();
        if((timeAfter - timeBefore) <= MIN_TIME_TO_FIND){
            try {
                sleep((long) (MIN_TIME_TO_FIND - (timeAfter - timeBefore)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return edge;
    }
}
