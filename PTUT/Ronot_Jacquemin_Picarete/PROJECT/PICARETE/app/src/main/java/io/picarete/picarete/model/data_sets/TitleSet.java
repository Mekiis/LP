package io.picarete.picarete.model.data_sets;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.picarete.picarete.R;
import io.picarete.picarete.game_logics.tools.XMLParser;
import io.picarete.picarete.model.container.userdata.Title;
import io.picarete.picarete.model.container.userdata.UserAccessor;

/**
 * Created by iem on 13/01/15.
 */
public class TitleSet {

    private static List<Title> titles = null;

    public static List<Title> getTitles(Context context){
        if (titles == null){
            titles = XMLParser.getTitles(context.getResources().openRawResource(R.raw.titles));
        }

        return titles;
    }

    public static List<String> getUnlockedTitles(Context context){
        List<String> unlockedTitles = new ArrayList<>();
        for(Title t : titles){
            if(t.isUnlocked(UserAccessor.getUser(context))){
                unlockedTitles.add(t.title);
            }
        }
        return unlockedTitles;
    }

    public static Title searchTitle(String titleName){
        Title titleSearch = null;

        for(Title title : titles){
            if(title.title == titleName)
                titleSearch = title;
        }

        return titleSearch;
    }
}
