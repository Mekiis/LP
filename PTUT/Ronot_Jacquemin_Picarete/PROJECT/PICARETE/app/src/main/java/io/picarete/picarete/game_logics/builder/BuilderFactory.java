package io.picarete.picarete.game_logics.builder;

import android.util.Log;

import io.picarete.picarete.game_logics.EGameMode;

/**
 * Created by root on 1/12/15.
 */
public class BuilderFactory {
    public static ABuilder getBuilder(EGameMode gameMode){
        ABuilder builder = new BuilderClassic();

        if(gameMode == EGameMode.CLASSIC || gameMode == EGameMode.CONTINUE_TO_PLAY){
            builder = new BuilderClassic();
        } else if(gameMode == EGameMode.TILE_GOOD){
            builder = new BuilderGoodTile();
        } else if(gameMode == EGameMode.TILE_BAD){
            builder = new BuilderBadTile();
        } else if(gameMode == EGameMode.EDGE_GOOD){
            builder = new BuilderGoodEdge();
        } else if(gameMode == EGameMode.EDGE_BAD){
            builder = new BuilderBadEdge();
        } else if(gameMode == EGameMode.BEST_AREA){
            builder = new BuilderBestArea();
        }  else {
            Log.d("BuilderFactory", "Game mode ("+gameMode.toString()+") is not recognized, default mode : "+EGameMode.CLASSIC.toString());
        }

        return builder;
    }
}
