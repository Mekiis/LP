package io.picarete.picarete.model.container.userdata;

import java.util.List;
import java.util.Map;

import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.game_logics.ia.EIA;
import io.picarete.picarete.model.EMode;
import io.picarete.picarete.model.data_sets.GameModeSet;
import io.picarete.picarete.model.data_sets.IASet;

/**
 * Created by iem on 13/01/15.
 */
public class Condition {

    Map<EConditionType, String> map;

    public enum EConditionType{
        GAME_MODE,
        MODE,
        WIN,
        LOST,
        PLAY,
        LEVEL,
        DIFFICULTY,
        TILE_USER,
        TILE_OPPONENT,
        RATIO
    }

    public Condition(Map<EConditionType, String> map) {
        this.map = map;
    }

    public boolean isConditionValidated(User user){
        EGameMode gameMode = null;
        if(map.containsKey(EConditionType.GAME_MODE)){
            gameMode = GameModeSet.searchGameMode(
                    map.get(EConditionType.GAME_MODE));
        }

        EIA ia = null;
        if(map.containsKey(EConditionType.DIFFICULTY)){
            ia = IASet.searchIA(map.get(EConditionType.DIFFICULTY));
        }

        EMode mode = null;
        if(map.containsKey(EConditionType.MODE)){
            mode = (map.get(EConditionType.MODE).compareToIgnoreCase(EMode.SOLO.toString()) == 0 ? EMode.SOLO : EMode.MULTI);
        }

        List<Stat> statsSearched = user.getStat(gameMode, ia, mode);
        int valueStatsFound = 0;

        if(map.containsKey(EConditionType.WIN)){
            for (Stat s : statsSearched){
                valueStatsFound += s.getWin();
            }
            int valueWin = Integer.parseInt(map.get(EConditionType.WIN));
            if(valueStatsFound < valueWin)
                return false;
        }
        valueStatsFound = 0;
        if(map.containsKey(EConditionType.LOST)){
            for (Stat s : statsSearched){
                valueStatsFound += s.getLost();
            }
            int valueLost = Integer.parseInt(map.get(EConditionType.LOST));
            if(valueStatsFound < valueLost)
                return false;
        }
        valueStatsFound = 0;
        if(map.containsKey(EConditionType.PLAY)){
            for (Stat s : statsSearched){
                valueStatsFound += s.getPlayed();
            }
            int valuePlayed = Integer.parseInt(map.get(EConditionType.PLAY));
            if(valueStatsFound < valuePlayed)
                return false;
        }
        valueStatsFound = 0;
        if(map.containsKey(EConditionType.TILE_USER)){
            for (Stat s : statsSearched){
                valueStatsFound += s.getTileUser();
            }
            int valuePlayed = Integer.parseInt(map.get(EConditionType.TILE_USER));
            if(valueStatsFound < valuePlayed)
                return false;
        }
        valueStatsFound = 0;
        if(map.containsKey(EConditionType.TILE_OPPONENT)){
            for (Stat s : statsSearched){
                valueStatsFound += s.getTileOpponent();
            }
            int valuePlayed = Integer.parseInt(map.get(EConditionType.TILE_OPPONENT));
            if(valueStatsFound < valuePlayed)
                return false;
        }

        return true;
    }
}
