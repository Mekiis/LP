package io.picarete.picarete.game_logics.ia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.picarete.picarete.game_logics.Game;
import io.picarete.picarete.game_logics.gameplay.Edge;
import io.picarete.picarete.game_logics.gameplay.Tile;
import io.picarete.picarete.model.NoDuplicatesList;

/**
 * Created by root on 1/7/15.
 */
public class AgressivIA extends AIA {
    @Override
    protected Edge findEdge(int height, int width, Game game, List<Edge> previousEdgesPlayed) {
        List<Edge> allEdgesPossible = new NoDuplicatesList<>();
        Map<Edge, Integer> allEdgesPossibleWithMaxTile = new HashMap<>();
        int bestClose  = 0;
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
                if(!allEdgesPossibleWithMaxTile.containsKey(edgeFree))
                    allEdgesPossibleWithMaxTile.put(edgeFree, 1);
                else
                    allEdgesPossibleWithMaxTile.put(edgeFree, allEdgesPossibleWithMaxTile.get(edgeFree)+1);

                bestClose = (allEdgesPossibleWithMaxTile.get(edgeFree) > bestClose ? allEdgesPossibleWithMaxTile.get(edgeFree)  : bestClose);
            }
        }
        for(Map.Entry<Edge, Integer> cursor : allEdgesPossibleWithMaxTile.entrySet()) {
            if(cursor.getValue() == bestClose)
                allEdgesPossible.add(cursor.getKey());
        }

        bestEdge = choseEdge(game.getTiles(), allEdgesPossible, previousEdgesPlayed);
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
        bestEdge = choseEdge(game.getTiles(), allEdgesPossible, previousEdgesPlayed);
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

        bestEdge = choseEdge(game.getTiles(), allEdgesPossible, previousEdgesPlayed);
        if(bestEdge != null)
            return bestEdge;
        else
            throw new ArithmeticException(this.getClass().getName()+ " - No such edge can be found with this algorithm");
    }

    public Edge choseEdge(List<Tile> game, List<Edge> edgesPossibles, List<Edge> previousEdges){
        Edge edge = null;

        edge = findNearest(game, edgesPossibles, previousEdges);

        return edge;
    }

    public Edge findNearest(List<Tile> game, List<Edge> edgesPossibles, List<Edge> previousEdges){
        Edge edge = null;

        if(previousEdges.size() == 0){
            if(edgesPossibles.size() > 0){
                Random r = new Random();
                int Low = 0;
                int High = edgesPossibles.size();
                int R = r.nextInt(High-Low) + Low;
                edge = edgesPossibles.get(R);
            }
        } else {
            Edge previousEdge = previousEdges.get(previousEdges.size()-1);
            List<Tile> tilesAssociatedWithPreviousEdge = findAssociatedTile(game, previousEdge);

            List<Edge> edgesWithMinDistancePossible = new NoDuplicatesList<>();

            double minDistance = -1;

            for(Edge e : edgesPossibles){
                List<Tile> tilesAssociatedWithEdgePossible = findAssociatedTile(game, e);
                double minDistanceForEdgePossible = getDistance(tilesAssociatedWithPreviousEdge, tilesAssociatedWithEdgePossible);
                if(minDistance == -1 || (minDistanceForEdgePossible != -1 && minDistanceForEdgePossible < minDistance)){
                    minDistance = minDistanceForEdgePossible;
                    edgesWithMinDistancePossible.clear();
                    edgesWithMinDistancePossible.add(e);
                } else if(minDistance == -1 || (minDistanceForEdgePossible != -1 && minDistanceForEdgePossible == minDistance)){
                    edgesWithMinDistancePossible.add(e);
                }
            }

            if(edgesWithMinDistancePossible.size() > 0){
                Random r = new Random();
                int Low = 0;
                int High = edgesWithMinDistancePossible.size();
                int R = r.nextInt(High-Low) + Low;
                edge = edgesWithMinDistancePossible.get(R);
            }
        }

        return edge;
    }

    private double getDistance(List<Tile> tilesList1, List<Tile> tilesList2) {
        double distanceMin = 0;

        for (Tile t : tilesList1){
            for (Tile t2 : tilesList2){
                int distance = (int) Math.sqrt(Math.pow(t.col - t2.col, 2.0) + Math.pow(t.row - t2.row, 2.0));
                distanceMin += distance;
            }
        }

        return distanceMin;
    }

    private List<Tile> findAssociatedTile(List<Tile> game, Edge edge) {
        List<Tile> tilesAssociated = new NoDuplicatesList<>();

        for (Tile t : game){
            for (Edge e : t.getEdges().values()){
                if(e == edge)
                    tilesAssociated.add(t);
            }
        }

        return tilesAssociated;
    }
}
