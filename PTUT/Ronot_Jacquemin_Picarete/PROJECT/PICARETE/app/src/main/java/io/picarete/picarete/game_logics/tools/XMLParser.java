package io.picarete.picarete.game_logics.tools;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.picarete.picarete.model.container.userdata.AUnlock;
import io.picarete.picarete.model.container.ColorCustom;
import io.picarete.picarete.model.container.userdata.Condition;
import io.picarete.picarete.model.container.userdata.Level;
import io.picarete.picarete.model.container.userdata.Title;
import io.picarete.picarete.model.container.userdata.UnlockColor;
import io.picarete.picarete.model.container.userdata.UnlockIA;
import io.picarete.picarete.model.container.userdata.UnlockMode;
import io.picarete.picarete.model.data_sets.GameModeSet;
import io.picarete.picarete.model.data_sets.IASet;

/**
 * Created by iem on 13/01/15.
 */
public class XMLParser {

    private static final String ELEMENT_CONFIG = "config";
    private static final String ELEMENT_TITLE = "title";
    private static final String ELEMENT_TITLE_VALUE = "value";
    private static final String ELEMENT_TITLE_CONDITION = "condition";

    private static List<Level> levels = new ArrayList<>();
    private static List<Title> titles = new ArrayList<>();
    private static Title title;

    public static List<Level> getLevels(InputStream in){

        if(levels.size() == 0)
            parse(in, 0);
        return levels;

    }

    public static List<Title> getTitles(InputStream in){

        if(titles.size() == 0)
            parse(in, 1);
        return titles;

    }

    private static void parse(InputStream in, int typeParse){

        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        parseXML(parser, typeParse);

    }

    private static void parseXML(XmlPullParser parser, int typeParse) {

        try{
            int eventType = parser.getEventType();
            title = new Title("", new ArrayList<Condition>());
            while (eventType != XmlPullParser.END_DOCUMENT){

                switch(typeParse) {
                    case 0:
                        parseConfig(parser, eventType);
                        break;
                    case 1:
                        parseTitles(parser, eventType);
                        break;
                }

                eventType = parser.next();

            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void parseTitles(XmlPullParser parser, int event) {

        switch (event) {

            case XmlPullParser.START_DOCUMENT:

                break;

            case XmlPullParser.START_TAG:
                String name = parser.getName();
                if (name.equalsIgnoreCase(ELEMENT_TITLE)) {

                    title = new Title("", new ArrayList<Condition>());

                }
                else if(name.equalsIgnoreCase(ELEMENT_TITLE_VALUE)){

                    try {
                        title.title = parser.nextText();
                    } catch (XmlPullParserException | IOException e) {
                        e.printStackTrace();
                    }

                }else if(name.equalsIgnoreCase(ELEMENT_TITLE_CONDITION)){

                    Map<Condition.EConditionType, String> map = new HashMap<>();

                    String GAME_MODE = parser.getAttributeValue(null, "game_mode");
                    String MODE = parser.getAttributeValue(null, "mode");
                    String WIN = parser.getAttributeValue(null, "win");
                    String LOST = parser.getAttributeValue(null, "lost");
                    String PLAY = parser.getAttributeValue(null, "play");
                    String LEVEL = parser.getAttributeValue(null, "level");
                    String DIFFICULTY = parser.getAttributeValue(null, "difficulty");
                    String TILE_USER = parser.getAttributeValue(null, "tile_user");
                    String TILE_OPPONENT = parser.getAttributeValue(null, "tile_opponent");
                    String RATIO = parser.getAttributeValue(null, "ratio");

                    if(GAME_MODE != null){
                        map.put(Condition.EConditionType.GAME_MODE, GAME_MODE);
                        Log.d("PARSE TITLE", GAME_MODE);
                    }

                    if(MODE != null){
                        map.put(Condition.EConditionType.MODE, MODE);
                        Log.d("PARSE TITLE", MODE);
                    }

                    if(WIN != null){
                        map.put(Condition.EConditionType.WIN, WIN);
                        Log.d("PARSE TITLE", WIN);
                    }

                    if(LOST != null){
                        map.put(Condition.EConditionType.LOST, LOST);
                        Log.d("PARSE TITLE", LOST);
                    }

                    if(PLAY != null){
                        map.put(Condition.EConditionType.PLAY, PLAY);
                        Log.d("PARSE TITLE", PLAY);
                    }

                    if(LEVEL != null){
                        map.put(Condition.EConditionType.LEVEL, LEVEL);
                        Log.d("PARSE TITLE", LEVEL);
                    }

                    if(DIFFICULTY != null){
                        map.put(Condition.EConditionType.DIFFICULTY, DIFFICULTY);
                        Log.d("PARSE TITLE", DIFFICULTY);
                    }

                    if(TILE_USER != null){
                        map.put(Condition.EConditionType.TILE_USER, TILE_USER);
                        Log.d("PARSE TITLE", TILE_USER);
                    }

                    if(TILE_OPPONENT != null){
                        map.put(Condition.EConditionType.TILE_OPPONENT, TILE_OPPONENT);
                        Log.d("PARSE TITLE", TILE_OPPONENT);
                    }

                    if(RATIO != null){
                        map.put(Condition.EConditionType.RATIO, RATIO);
                        Log.d("PARSE TITLE", RATIO);
                    }

                    Condition condition = new Condition(map);
                    title.conditions.add(condition);
                }
                break;
            case XmlPullParser.END_TAG:
                if(parser.getName().equalsIgnoreCase(ELEMENT_TITLE))
                    titles.add(title);
                break;
        }
    }

    private static void parseConfig(XmlPullParser parser, int event){
        String name = parser.getName();
        Level currentLevel;
        switch (event){

            case XmlPullParser.START_DOCUMENT:

                break;

            case XmlPullParser.START_TAG:

                if (name.equalsIgnoreCase(ELEMENT_CONFIG)) {
                    String lvl = parser.getAttributeValue(null, "level");
                    String color = parser.getAttributeValue(null, "color");
                    String mode = parser.getAttributeValue(null, "mode");
                    String ia = parser.getAttributeValue(null, "ia");
                    String row = parser.getAttributeValue(null, "row");
                    String column = parser.getAttributeValue(null, "column");

                    currentLevel = new Level(new LinkedList<AUnlock>());

                    currentLevel.id = Integer.parseInt(lvl);

                    if (color != null) {
                        String[] allColorsForLevel = color.split("[|]");
                        for (String colorForLevel : allColorsForLevel){
                            UnlockColor unlockColor = new UnlockColor(new ColorCustom(colorForLevel));
                            currentLevel.unlocks.add(unlockColor);
                            Log.d("PARSE LEVELS", color);
                        }
                    }

                    if (mode != null) {
                        UnlockMode unlockMode = new UnlockMode(GameModeSet.searchGameMode(mode));
                        currentLevel.unlocks.add(unlockMode);
                        Log.d("PARSE LEVELS", mode);
                    }

                    if (ia != null) {
                        UnlockIA unlockIa = new UnlockIA(IASet.searchIA(ia));
                        currentLevel.unlocks.add(unlockIa);
                        Log.d("PARSE LEVELS", ia);
                    }

                    if (row != null) {
                        currentLevel.row = Integer.parseInt(row);
                        Log.d("PARSE LEVELS", row);
                    }

                    if (column != null) {
                        currentLevel.column = Integer.parseInt(column);
                        Log.d("PARSE LEVELS", column);
                    }

                    levels.add(currentLevel);
                }

            case XmlPullParser.END_TAG:

                name = parser.getName();
                break;
        }

    }
}

