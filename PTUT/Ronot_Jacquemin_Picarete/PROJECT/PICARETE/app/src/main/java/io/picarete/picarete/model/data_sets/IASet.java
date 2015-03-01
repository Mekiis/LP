package io.picarete.picarete.model.data_sets;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.picarete.picarete.R;
import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.game_logics.ia.EIA;
import io.picarete.picarete.model.container.GameModeCustom;
import io.picarete.picarete.model.container.IACustom;

/**
 * Created by root on 1/12/15.
 */
public class IASet {
    public static Map<EIA, IACustom> IAs = null;

    public static void constructListIas(Context context){
        IAs = new LinkedHashMap<>();

        String[] iaName = context.getResources().getStringArray(R.array.ia_difficulty);

        IAs.put(EIA.EASY, new IACustom(iaName[0], 0));
        IAs.put(EIA.EASY_MAX_TILE, new IACustom(iaName[1], 1));
        IAs.put(EIA.AGGRESSIVE, new IACustom(iaName[2], 2));
        IAs.put(EIA.MCTS, new IACustom(iaName[3], 3));
    }

    public static Map<EIA, IACustom> getIAs(Context context){
        if(IAs == null)
            constructListIas(context);

        return IAs;
    }

    public static String[] getNames(Context context){
        if(IAs == null)
            constructListIas(context);

        String[] name = new String[IAs.size()];
        List<IACustom> IAsArr = new LinkedList<>(IAs.values());

        for (int i = 0; i< IAs.size(); i++){
            name[i] = IAsArr.get(i).name;
        }

        return name;
    }

    public static int[] getRating(Context context){
        if(IAs == null)
            constructListIas(context);

        int[] rating = new int[IAs.size()];
        List<IACustom> IAsArr = new ArrayList<>(IAs.values());

        for (int i = 0; i< IAs.size(); i++){
            rating[i] = IAsArr.get(i).rating;
        }

        return rating;
    }

    public static EIA[] getEIAs(Context context){
        if(IAs == null)
            constructListIas(context);

        EIA[] IAsArr = new EIA[IAs.keySet().size()];
        int i = 0;
        for(EIA e : IAs.keySet()){
            IAsArr[i] = e;
            i++;
        }


        return IAsArr;
    }

    public static EIA searchIA(String iaName){
        EIA ia;

        if(iaName.compareToIgnoreCase(EIA.EASY.toString()) == 0)
            ia = EIA.EASY;
        else if(iaName.compareToIgnoreCase(EIA.EASY_MAX_TILE.toString()) == 0)
            ia = EIA.EASY_MAX_TILE;
        else if(iaName.compareToIgnoreCase(EIA.AGGRESSIVE.toString()) == 0)
            ia = EIA.AGGRESSIVE;
        else if(iaName.compareToIgnoreCase(EIA.MINIMAX.toString()) == 0)
            ia = EIA.MINIMAX;
        else if(iaName.compareToIgnoreCase(EIA.MCTS.toString()) == 0)
            ia = EIA.MCTS;
        else
            ia = EIA.EASY;

        return ia;
    }

    public static String[] getNames(Context context, List<EIA> iaForLevel) {
        if(IAs == null)
            constructListIas(context);

        String[] names = new String[iaForLevel.size()];

        for (int i = 0; i< iaForLevel.size(); i++){
            if(IAs.containsKey(iaForLevel.get(i)))
                names[i] = IAs.get(iaForLevel.get(i)).name;
        }

        return names;
    }
}
