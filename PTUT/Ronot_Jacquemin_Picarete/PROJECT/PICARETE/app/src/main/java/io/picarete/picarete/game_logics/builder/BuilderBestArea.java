package io.picarete.picarete.game_logics.builder;

import android.util.Log;

import io.picarete.picarete.game_logics.Game;
import io.picarete.picarete.game_logics.gameplay.ETileSide;
import io.picarete.picarete.game_logics.gameplay.Edge;
import io.picarete.picarete.game_logics.gameplay.Tile;
import io.picarete.picarete.game_logics.gameplay.TileBrother;

/**
 * Created by root on 1/12/15.
 */
public class BuilderBestArea extends ABuilder {
    public static final int PROBA_SPECIAL_TILE = 1;

    public static final int PERCENT_MIN_TILE_CHOSEN = 10;
    public static final int PERCENT_MAX_TILE_CHOSEN = 20;

    @Override
    protected void createBase(int height, int width, Game game){
        int idEdge = 0;
        for(int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                Edge left;
                Edge top;
                Edge right = new Edge(idEdge);
                idEdge++;
                Edge bottom = new Edge(idEdge);
                idEdge++;
                if(i == 0){
                    top = new Edge(idEdge);
                    idEdge++;
                } else {
                    top = tiles.get((i-1)*width+j).getEdges().get(ETileSide.BOTTOM);
                    Log.d(this.getClass().getName(), "For UITile " + (i * width + j) + " / Top : " + Integer.toString((i - 1) * width + j));
                }

                if(j == 0){
                    left = new Edge(idEdge);
                    idEdge++;
                } else {
                    left = tiles.get(i*width+j-1).getEdges().get(ETileSide.RIGHT);
                    Log.d(this.getClass().getName(), "For UITile "+(i*width+j)+" / Left : "+Integer.toString((i)*width+j-1));
                }

                Tile t = new TileBrother(i*width+j, left, top, right, bottom);
                t.row = i;
                t.col = j;
                t.setEventListener(game);
                tiles.add(t);
            }
        }

        for(int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                if(tiles.get(i*width+j) instanceof TileBrother)
                    linkBrotherTile((TileBrother) tiles.get(i*width+j), i, j, width, height);
            }
        }
    }

    @Override
    protected void setupSpecialElements() {

    }

    private void linkBrotherTile(TileBrother tile, int i, int j, int width, int height){
        TileBrother leftTile = null, topTile = null, rightTile = null, bottomTile = null;

        if(i != 0 && tiles.get((i-1)*width+j) instanceof TileBrother){
            topTile = (TileBrother) tiles.get((i-1)*width+j);
        }

        if(j != 0 && tiles.get(i*width+j-1) instanceof TileBrother){
            leftTile = (TileBrother) tiles.get(i*width+j-1);
        }

        if(i < height-1 && tiles.get((i+1)*width+j) instanceof TileBrother){
            bottomTile = (TileBrother) tiles.get((i+1)*width+j);
        }

        if(j < width-1 && tiles.get(i*width+j+1) instanceof TileBrother){
            rightTile = (TileBrother) tiles.get(i*width+j+1);
        }

        tile.linkBrothers(leftTile, topTile, rightTile, bottomTile);

    }
}
