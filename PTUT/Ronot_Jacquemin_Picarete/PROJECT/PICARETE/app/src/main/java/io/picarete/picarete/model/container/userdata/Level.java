package io.picarete.picarete.model.container.userdata;

import java.util.LinkedList;
import java.util.List;

import io.picarete.picarete.model.Constants;

/**
 * Created by iem on 13/01/15.
 */
public class Level {
    public int id;
    public List<AUnlock> unlocks;
    public int row;
    public int column;

    public Level(LinkedList<AUnlock> aUnlocks) {
        this.unlocks = aUnlocks;
        row = Constants.COLUMN_ROW_MIN;
        column = Constants.COLUMN_ROW_MIN;
    }

}
