package io.picarete.picarete.game_logics.ia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.picarete.picarete.game_logics.Game;
import io.picarete.picarete.game_logics.gameplay.Edge;
import io.picarete.picarete.game_logics.gameplay.Tile;
import io.picarete.picarete.model.NoDuplicatesList;

/**
 * Created by root on 1/7/15.
 */
public class SimpleIA extends AIA {
    @Override
    protected Edge findEdge(int height, int width, Game game, List<Edge> previousEdgesPlayed) {
        List<Edge> allEdgesPossible = new NoDuplicatesList<>();
        Edge bestEdge = null;

        // Search to complete an existing UITile
        for(Tile t : game.getTiles()){
            int nbEdgeFree = 4;
            Edge edgeFree = null;
            for(Edge e : t.getEdges().values()){
                if(e.isChosen()){
                    nbEdgeFree--;

                } else {
                    edgeFree = e;
                }
            }
            if(nbEdgeFree == 1){
                allEdgesPossible.add(edgeFree);
            }
        }
        bestEdge = choseEdge(allEdgesPossible);
        if(bestEdge != null)
            return bestEdge;

        // Search to chose an edge free and with more 2 edge free
        allEdgesPossible.clear();
        List<Edge> badEdge = new NoDuplicatesList<>();
        List<Edge> possibleGoodEdge = new NoDuplicatesList<>();
        for(Tile t : game.getTiles()){
            int nbEdgeFree = 4;
            List<Edge> edgesFree = new ArrayList<>();
            for(Edge e : t.getEdges().values()){
                if(e.isChosen()){
                    nbEdgeFree--;
                } else {
                    edgesFree.add(e);
                }
            }
            if(nbEdgeFree > 2){
                for(Edge e : edgesFree)
                    possibleGoodEdge.add(e);
            } else {
                for(Edge e : edgesFree)
                    badEdge.add(e);
            }
        }
        for(Edge e : possibleGoodEdge){
            if(!badEdge.contains(e))
                allEdgesPossible.add(e);
        }
        bestEdge = choseEdge(allEdgesPossible);
        if(bestEdge != null)
            return bestEdge;

        // Search to chose a free edge
        allEdgesPossible.clear();
        for(Tile t : game.getTiles()){
            List<Edge> edgesFree = new ArrayList<>();;
            for(Edge e : t.getEdges().values()){
                if(!e.isChosen()){
                    edgesFree.add(e);
                }
            }

            for(Edge e : edgesFree)
                allEdgesPossible.add(e);
        }
        if(allEdgesPossible.size() > 0) {
            Random r = new Random();
            int Low = 0;
            int High = allEdgesPossible.size();
            int R = r.nextInt(High - Low) + Low;
            return allEdgesPossible.get(R);
        } else {
            throw new ArithmeticException(this.getClass().getName()+ " - No such edge can be found with this algorithm");
        }
    }

    public Edge choseEdge(List<Edge> edgesPossibles){
        if(edgesPossibles.size() > 0){
            Random r = new Random();
            int Low = 0;
            int High = edgesPossibles.size();
            int R = r.nextInt(High-Low) + Low;
            return edgesPossibles.get(R);
        }

        return null;
    }
}
