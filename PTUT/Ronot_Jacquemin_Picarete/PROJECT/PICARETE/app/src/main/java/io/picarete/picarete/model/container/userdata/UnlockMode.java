package io.picarete.picarete.model.container.userdata;

import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.model.container.userdata.AUnlock;

/**
 * Created by iem on 13/01/15.
 */
public class UnlockMode extends AUnlock {

    public UnlockMode(EGameMode gameMode) {
        this.gameMode = gameMode;
    }

    public EGameMode gameMode;

}
