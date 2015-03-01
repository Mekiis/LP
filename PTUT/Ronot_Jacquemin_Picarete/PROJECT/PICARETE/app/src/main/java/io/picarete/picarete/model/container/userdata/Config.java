package io.picarete.picarete.model.container.userdata;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.game_logics.ia.EIA;
import io.picarete.picarete.game_logics.tools.XMLParser;
import io.picarete.picarete.model.Constants;
import io.picarete.picarete.model.NoDuplicatesList;
import io.picarete.picarete.model.container.userdata.AUnlock;
import io.picarete.picarete.model.container.ColorCustom;
import io.picarete.picarete.model.container.userdata.Level;
import io.picarete.picarete.model.container.userdata.UnlockColor;
import io.picarete.picarete.model.container.userdata.UnlockIA;
import io.picarete.picarete.model.container.userdata.UnlockMode;

/**
 * Created by iem on 13/01/15.
 */
public class Config {

    private static List<Level> levels;

    private static void loadConfig(Context context){
        levels = new ArrayList<>();
        try {
            levels = XMLParser.getLevels(context.getAssets().open("config.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Level> getLevels(Context context){
        if(levels == null)
            loadConfig(context);
        return levels;
    }

    public static List<ColorCustom> getColors(int level){
        List<ColorCustom> colors = new NoDuplicatesList<>();

        for(Level lvl : levels){

            if(lvl.id<=level && lvl.unlocks!=null) {

                for (AUnlock unlock : lvl.unlocks) {
                    if (unlock instanceof UnlockColor)
                        colors.add(((UnlockColor) unlock).color);
                }

            }

        }
        return colors;
    }

    public static List<EIA> getIAs(int level){
        List<EIA> ias = new NoDuplicatesList<>();

        for(Level lvl : levels){

            if(lvl.id<=level && lvl.unlocks!=null) {

                for (AUnlock unlock : lvl.unlocks) {
                    if (unlock instanceof UnlockIA)
                        ias.add(((UnlockIA) unlock).ia);
                }
            }
        }
        return ias;
    }

    public static List<EGameMode> getGameModes(int level){
        List<EGameMode> gameModes = new NoDuplicatesList<>();
        for(Level lvl : levels){
            if(lvl.id<=level && lvl.unlocks!=null) {
                for (AUnlock unlock : lvl.unlocks) {
                    if (unlock instanceof UnlockMode)
                        gameModes.add(((UnlockMode) unlock).gameMode);
                }
            }
        }
        return gameModes;
    }

    public static int getRow(int level){
        int row = Constants.COLUMN_ROW_MIN;
        for(int i = 0 ; i < levels.size() ; i ++){
            if(levels.get(i).id <= level){
                row = Math.max(row, levels.get(i).row);
            }
        }
        return row;
    }

    public static int getColumn(int level){
        int column = Constants.COLUMN_ROW_MIN;
        for(int i = 0 ; i < levels.size() ; i ++){
            if(levels.get(i).id <= level){
                column = Math.max(column, levels.get(i).column);
            }
        }
        return column;
    }
}
