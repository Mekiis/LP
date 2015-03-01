package io.picarete.picarete.game_logics.builder;

import android.util.Log;

import java.util.Random;

import io.picarete.picarete.game_logics.Game;
import io.picarete.picarete.game_logics.gameplay.ETileSide;
import io.picarete.picarete.game_logics.gameplay.Edge;
import io.picarete.picarete.game_logics.gameplay.Tile;
import io.picarete.picarete.game_logics.gameplay.TileGood;

/**
 * Created by root on 1/12/15.
 */
public class BuilderGoodTile extends ABuilder {
    public static final int PROBA_SPECIAL_TILE = 3;

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

                Tile t = generateTile(i*width+j, left, top, right, bottom);
                t.row = i;
                t.col = j;
                t.setEventListener(game);
                tiles.add(t);
            }
        }
    }

    @Override
    protected void setupSpecialElements() {
        Random r = new Random();
        int percentOfEdges = r.nextInt(PERCENT_MAX_TILE_CHOSEN - PERCENT_MIN_TILE_CHOSEN + 1) + PERCENT_MIN_TILE_CHOSEN;
        int numberOfEdge = percentOfEdges * getAllEdges().size() / 100;

        for (int i = 0; i < numberOfEdge; i++){
            int edgeID = new Random().nextInt(getAllEdges().size());
            Edge edge = getAllEdges().get(edgeID);
            if(!edge.isChosen()){
                edge.setChosen(true);
            }
        }
    }

    private boolean hasToGenerateSpecialTile(){
        Random r = new Random();
        int percentOfEdges = r.nextInt(PROBA_SPECIAL_TILE - 1 + 1) + 1;
        return (percentOfEdges == 1 ? true : false);
    }

    private Tile generateTile(int id, Edge left, Edge top, Edge right, Edge bottom){
        Tile t;

        //i*width+j
        if(hasToGenerateSpecialTile())
            t = generateSpecialTile(id, left, top, right, bottom);
        else
            t = new Tile(id, left, top, right, bottom);

        return t;
    }

    private Tile generateSpecialTile(int id, Edge left, Edge top, Edge right, Edge bottom){
        return new TileGood(id, left, top, right, bottom);
    }
}
