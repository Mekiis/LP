package io.picarete.picarete.model.container.userdata;

import java.io.Serializable;

/**
 * Created by iem on 14/01/15.
 */
public class StatGame {

    private int idWinner = -1;
    public int tileP1 = -1;
    public int tileP2 = -1;
    public int scoreP1 = -1;
    public int scoreP2 = -1;
    public int tileNeutral = -1;

    public int getIdWinner() {
        return idWinner;
    }

    public void setIdWinner(int idWinner) {
        if(idWinner == 0)
            this.idWinner = -1;
        else if (idWinner == -1)
            this.idWinner = 0;
        else if (idWinner == 1)
            this.idWinner = 1;
    }
}
