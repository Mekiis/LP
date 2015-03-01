package io.picarete.picarete.game_logics;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.picarete.picarete.game_logics.builder.BuilderFactory;
import io.picarete.picarete.game_logics.gameplay.Edge;
import io.picarete.picarete.game_logics.gameplay.EdgeBad;
import io.picarete.picarete.game_logics.gameplay.EdgeGood;
import io.picarete.picarete.game_logics.gameplay.Tile;
import io.picarete.picarete.game_logics.gameplay.TileBrother;
import io.picarete.picarete.model.NoDuplicatesList;


public class Game implements Tile.TileEventListener{
    public int idPlayer = 0;
    public Map<Integer, Integer> scores = null;

    public List<Tile> tiles = null;
    public int width = 0;
    public int height = 0;
    public EGameMode gameMode = EGameMode.CLASSIC;

    private List<Edge> edgesPreviousPlayed = null;

    public Context context = null;

    // Event Management
    GameEventListener eventListener = null;

    public Map<Integer, Integer> getScores() {
        return scores;
    }

    public interface GameEventListener {
        public abstract void OnFinished();

        public abstract void OnMajGUI(int idPlayerActual);

        public abstract void OnMajTile(List<Tile> tilesToRedraw);

        public abstract void OnNextPlayer(int idPlayerActual);
    }

    public void setEventListener(GameEventListener e){
        this.eventListener = e;
    }

    // Constructor
    public Game(Context context, EGameMode gameMode, int height, int width, int idPlayer){
        this.tiles = new LinkedList<>();
        this.edgesPreviousPlayed = new LinkedList<>();
        this.scores = new HashMap<>();
        this.scores.put(0, 0);
        this.scores.put(1, 0);

        this.gameMode = gameMode;
        this.height = height;
        this.width = width;

        this.context = context;

        this.idPlayer = idPlayer;
    }

    @Override
    public void OnClick(Tile tile, Edge edge) {
        boolean hasCompletedATile = false;

        edgesPreviousPlayed.add(edge);
        edge.setIdPlayer(idPlayer);

        if(edge instanceof EdgeGood || edge instanceof EdgeBad){
            addScoreForPlayer(idPlayer, edge.getScoreForPlayer());
        }

        List<Tile> neighborTilesFromEdge = findNeighborFromEdge(edge);
        List<Tile> tilesToRedraw = findNeighborFromTile(tile);
        List<Tile> tilesCompleted = new NoDuplicatesList<>();

        for(Tile t : neighborTilesFromEdge){
            if(t.isComplete() && t.getIdPlayer() == -1){
                t.setIdPlayer(idPlayer);
                hasCompletedATile = true;
                tilesCompleted.add(t);
            }
            tilesToRedraw.add(t);
        }

        for(Tile t : tilesCompleted){
            if(gameMode == EGameMode.BEST_AREA && t instanceof TileBrother){
                List<Tile> tileBrothers = ((TileBrother) t).getScoreBrothers(new ArrayList<Tile>());
                addScoreForPlayer(idPlayer, tileBrothers.size());
            } else {
                addScoreForPlayer(idPlayer, t.getScoreForPlayer());
            }
        }

        if(eventListener != null)
            eventListener.OnMajTile(tilesToRedraw);

        if(gameMode != EGameMode.CONTINUE_TO_PLAY || (gameMode == EGameMode.CONTINUE_TO_PLAY && !hasCompletedATile)){
            idPlayer = (idPlayer + 1) % 2;
        }

        if(eventListener != null)
            eventListener.OnMajGUI(idPlayer);

        if(isGameEnd()){
            if(eventListener != null)
                eventListener.OnFinished();
            return;
        }

        if(eventListener != null)
            eventListener.OnNextPlayer(idPlayer);
    }

    private void addScoreForPlayer(int idPlayer, int score){
        scores.put(idPlayer, scores.get(idPlayer)+score);
    }

    public List<Tile> findNeighborFromEdge(Edge edge){
        List<Tile> tilesNeighbor = new NoDuplicatesList<Tile>();

        for(int i = 0; i < tiles.size(); i++){
            for(Edge e : tiles.get(i).getEdges().values()){
                if(e == edge)
                    tilesNeighbor.add(tiles.get(i));
            }
        }

        return tilesNeighbor;
    }

    public List<Tile> findNeighborFromTile(Tile tile){
        List<Tile> tilesNeighbor = new NoDuplicatesList<>();

        for(int i = 0; i < tiles.size(); i++){
            for(Edge e : tiles.get(i).getEdges().values()){
                for (Tile t : findNeighborFromEdge(e))
                    tilesNeighbor.add(t);
            }
        }

        return tilesNeighbor;
    }

    public boolean isGameEnd(){
        boolean isGameEnd = true;

        for(int i = 0; i < tiles.size(); i++){
            if(!tiles.get(i).isComplete())
                isGameEnd = false;
        }

        return isGameEnd;
    }

    public List<Tile> createGame(boolean needChosenBorderTile, boolean needChosenTile){
        tiles = BuilderFactory.getBuilder(gameMode).createGame(height, width, this, needChosenBorderTile, needChosenTile);

        return tiles;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<Tile> getTilesForPlayer(int idPlayer){
        List<Tile> tilesForPlayer = new ArrayList<>();

        for(Tile t : tiles){
            if(t.getIdPlayer() == idPlayer)
                tilesForPlayer.add(t);
        }

        return tilesForPlayer;
    }

    public List<Edge> getEdgesPreviousPlayed() {
        return edgesPreviousPlayed;
    }
}
