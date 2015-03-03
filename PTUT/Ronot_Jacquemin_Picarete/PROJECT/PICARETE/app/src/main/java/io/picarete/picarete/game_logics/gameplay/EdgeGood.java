package io.picarete.picarete.game_logics.gameplay;

/**
 * Created by root on 1/12/15.
 */
public class EdgeGood extends Edge {
    public EdgeGood(int id) {
        super(id);
    }

    @Override
    public int getScoreForPlayer() {
        return 1;
    }
}
