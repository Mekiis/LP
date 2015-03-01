package io.picarete.picarete.game_logics;

import java.io.Serializable;

/**
 * Created by root on 1/12/15.
 */
public enum EGameMode implements Serializable {
    CLASSIC,
    EDGE_BAD,
    EDGE_GOOD,
    TILE_BAD,
    TILE_GOOD,
    BEST_AREA,
    CONTINUE_TO_PLAY
};
