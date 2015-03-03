package io.picarete.picarete.game_logics.gameplay;

/**
 * Created by root on 1/12/15.
 */
public class EdgeBad extends Edge {
    public EdgeBad(int id) {
        super(id);
    }

    @Override
    public int getScoreForPlayer() {
        return -1;
    }
}
