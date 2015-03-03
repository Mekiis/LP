package io.picarete.picarete.game_logics.builder;

import android.util.Log;

import java.util.List;
import java.util.Random;

import io.picarete.picarete.game_logics.Game;
import io.picarete.picarete.game_logics.gameplay.ETileSide;
import io.picarete.picarete.game_logics.gameplay.Edge;
import io.picarete.picarete.game_logics.gameplay.EdgeGood;
import io.picarete.picarete.game_logics.gameplay.Tile;

/**
 * Created by root on 1/12/15.
 */
public class BuilderGoodEdge extends ABuilder {

    private static final int PROBA_SPECIAL_EDGE = 3;

    private static final int PERCENT_MIN_EDGES = 20;
    private static final int PERCENT_MAX_EDGES = 30;

    private int nbSpecialEdges = 0;

    @Override
    public List<Tile> createGame(int height, int width, Game game, boolean needChosenBorderTile, boolean needChosenTile) {
        nbSpecialEdges = 0;
        return super.createGame(height, width, game, needChosenBorderTile, needChosenTile);
    }

    @Override
    protected void setupSpecialElements() {
        // Setup a minimum of Special Edge
        Random r = new Random();
        int percentOfEdges = r.nextInt(PERCENT_MAX_EDGES - PERCENT_MIN_EDGES + 1) + PERCENT_MIN_EDGES;
        int numberOfEdge = percentOfEdges * getAllEdges().size() / 100;

        if(nbSpecialEdges < numberOfEdge){
            int nbEdgeToBeSpecial = numberOfEdge - nbSpecialEdges;
            for (int i = 0; i < nbEdgeToBeSpecial; i++){
                int edgeID = new Random().nextInt(getAllEdges().size());
                Edge e = getAllEdges().get(edgeID);
                Edge eSpecial = generateSpecialEdge(e.id);
                for (Tile t : tiles){
                    for (ETileSide key : t.getEdges().keySet()){
                        if(t.getEdges().get(key) == e){
                            t.getEdges().put(key, eSpecial);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void createBase(int height, int width, Game game) {
        // Create all edges and tiles
        int idEdge = 0;
        for(int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                Edge left;
                Edge top;
                Edge right = generateEdge(idEdge);
                idEdge++;
                Edge bottom = generateEdge(idEdge);
                idEdge++;
                if(i == 0){
                    top = generateEdge(idEdge);
                    idEdge++;
                } else {
                    top = tiles.get((i-1)*width+j).getEdges().get(ETileSide.BOTTOM);
                    Log.d(this.getClass().getName(), "For UITile " + (i * width + j) + " / Top : " + Integer.toString((i - 1) * width + j));
                }

                if(j == 0){
                    left = generateEdge(idEdge);
                    idEdge++;
                } else {
                    left = tiles.get(i*width+j-1).getEdges().get(ETileSide.RIGHT);
                    Log.d(this.getClass().getName(), "For UITile "+(i*width+j)+" / Left : "+Integer.toString((i)*width+j-1));
                }

                Tile t = new Tile(i*width+j, left, top, right, bottom);
                t.row = i;
                t.col = j;
                t.setEventListener(game);
                tiles.add(t);
            }
        }
    }

    private boolean hasToGenerateSpecialEdge(){
        Random r = new Random();
        int percentOfEdges = r.nextInt(PROBA_SPECIAL_EDGE - 1 + 1) + 1;
        return (percentOfEdges == 1 ? true : false);
    }

    private Edge generateEdge(int idEdge){
        Edge e;

        if(hasToGenerateSpecialEdge()){
            nbSpecialEdges++;
            e = generateSpecialEdge(idEdge);
        }
        else
            e = new Edge(idEdge);

        return e;
    }

    private Edge generateSpecialEdge(int idEdge){
        return new EdgeGood(idEdge);
    }
}
