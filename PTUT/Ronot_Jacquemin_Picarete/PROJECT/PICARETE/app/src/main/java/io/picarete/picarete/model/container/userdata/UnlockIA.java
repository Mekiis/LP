package io.picarete.picarete.model.container.userdata;

import io.picarete.picarete.game_logics.ia.EIA;
import io.picarete.picarete.model.container.userdata.AUnlock;

/**
 * Created by iem on 13/01/15.
 */
public class UnlockIA  extends AUnlock {

    public EIA ia;

    public UnlockIA(EIA ia) {
        this.ia = ia;
    }
}
