package io.picarete.picarete.model.container.userdata;

import io.picarete.picarete.model.container.ColorCustom;

/**
 * Created by iem on 13/01/15.
 */
public class UnlockColor extends AUnlock {

    public ColorCustom color;

    public UnlockColor(ColorCustom color) {
        this.color = color;
    }
}
