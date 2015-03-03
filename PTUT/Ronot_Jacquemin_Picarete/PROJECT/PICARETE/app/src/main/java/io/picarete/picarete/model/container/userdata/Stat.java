package io.picarete.picarete.model.container.userdata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.game_logics.ia.EIA;
import io.picarete.picarete.model.EMode;

/**
 * Created by iem on 13/01/15.
 */
public class Stat implements Serializable{

    public EIA ia;
    public EGameMode gameMode;
    public EMode mode;
    public List<StatGame> statGames;

    public Stat(EIA ia, EGameMode gameMode, EMode mode) {
        this.ia = ia;
        this.gameMode = gameMode;
        this.mode = mode;
        this.statGames = new ArrayList<>();
    }

    public int getWin(){
        int win = 0;
        for(StatGame stat : statGames){
            if(stat.scoreP1>stat.scoreP2)
                win++;
        }
        return win;
    }
     public int getLost(){
         int lost = 0;
         for(StatGame stat : statGames){
             if(stat.scoreP1<stat.scoreP2)
                 lost++;
         }
         return lost;
     }

    public int getPlayed(){
        return statGames.size();
    }

    public int getTileUser() {
        int tileUser = 0;
        for(StatGame stat : statGames){
            tileUser+=stat.tileP1;
        }
        return tileUser;
    }

    public int getTileOpponent() {
        int tileOpponent = 0;
        for(StatGame stat : statGames){
            tileOpponent+=stat.tileP2;
        }
        return tileOpponent;
    }
}
