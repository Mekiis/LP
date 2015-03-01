package io.picarete.picarete.game_logics.builder;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.picarete.picarete.game_logics.Game;
import io.picarete.picarete.game_logics.gameplay.ETileSide;
import io.picarete.picarete.game_logics.gameplay.Edge;
import io.picarete.picarete.game_logics.gameplay.Tile;

/**
 * Created by root on 1/12/15.
 */
public abstract class ABuilder {
    protected List<Tile> tiles = null;

    private static final int PERCENT_MIN_EDGE_CHOSEN = 10;
    private static final int PERCENT_MAX_EDGE_CHOSEN = 40;

    public List<Tile> createGame(int height, int width, Game game, boolean needChosenBorderTile, boolean needChosenTile){
        tiles = new ArrayList<>();
        createBase(height, width, game);
        if(needChosenBorderTile)
            setupBorder(height, width);
        if (needChosenTile)
            setupChosenEdge();
        setupSpecialElements();

        return tiles;
    }

    protected abstract void setupSpecialElements();

    protected void setupChosenEdge(){
        // Setup a minimum of Chosen Edge
        Random r = new Random();
        int percentOfEdgesChosen = r.nextInt(PERCENT_MAX_EDGE_CHOSEN - PERCENT_MIN_EDGE_CHOSEN + 1) + PERCENT_MIN_EDGE_CHOSEN;
        int numberOfEdgeChosen = percentOfEdgesChosen * getAllEdges().size() / 100;

        for (int i = 0; i < numberOfEdgeChosen; i++){
            int edgeID = new Random().nextInt(getAllEdges().size());
            Edge edge = getAllEdges().get(edgeID);
            if(!edge.isChosen()){
                edge.setChosen(true);
            }
        }
    }

    protected void setupBorder(int height, int width){
        // Setup the border of grid to chosen Edge
        for(int i = 0; i < tiles.size(); i++){
            int xPosition = i % width;
            int yPosition = i / width;
            if(xPosition == 0)
                tiles.get(i).getEdges().get(ETileSide.LEFT).setChosen(true);
            if(yPosition == 0)
                tiles.get(i).getEdges().get(ETileSide.TOP).setChosen(true);
            if(xPosition == width-1)
                tiles.get(i).getEdges().get(ETileSide.RIGHT).setChosen(true);
            if(yPosition == height-1)
                tiles.get(i).getEdges().get(ETileSide.BOTTOM).setChosen(true);
        }
    }

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

                Tile t = new Tile(i*width+j, left, top, right, bottom);
                t.row = i;
                t.col = j;
                t.setEventListener(game);
                tiles.add(t);
            }
        }
    }

    protected List<Edge> getAllEdges(){
        List<Edge> edges = new ArrayList<>();

        if(tiles != null){
            for (Tile t : tiles){
                for(Edge e : t.getEdges().values()){
                    if(!edges.contains(e))
                        edges.add(e);
                }

            }
        }

        return edges;

    }

}
