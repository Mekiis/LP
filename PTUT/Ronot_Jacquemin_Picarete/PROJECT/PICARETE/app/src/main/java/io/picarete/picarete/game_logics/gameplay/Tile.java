package io.picarete.picarete.game_logics.gameplay;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Tile{
    private Map<ETileSide, Edge> edges;
    private int idPlayer = -1;
    public int id = -1;
    public int row = -1;
    public int col = -1;

    // Event Management
    TileEventListener eventListener = null;

    public Map<ETileSide, Edge> getEdges() {
        return edges;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getScoreForPlayer() {
        return 1;
    }

    public interface TileEventListener {
        public abstract void OnClick(Tile l, Edge edge);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;

        Tile tile = (Tile) o;

        if (col != tile.col) return false;
        if (id != tile.id) return false;
        if (row != tile.row) return false;
        if (!edges.equals(tile.edges)) return false;
        if (eventListener != null ? !eventListener.equals(tile.eventListener) : tile.eventListener != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = edges.hashCode();
        result = 31 * result + id;
        result = 31 * result + row;
        result = 31 * result + col;
        result = 31 * result + (eventListener != null ? eventListener.hashCode() : 0);
        return result;
    }

    public void setEventListener(TileEventListener e){
        this.eventListener = e;
    }

    public Tile(int id, Edge left, Edge top, Edge right, Edge bottom){
        edges = new HashMap<>();
        edges.put(ETileSide.LEFT, left);
        edges.put(ETileSide.TOP, top);
        edges.put(ETileSide.RIGHT, right);
        edges.put(ETileSide.BOTTOM, bottom);

        this.id = id;
    }

    public void onClick(Edge edge){

        if(!edge.isChosen()){
            edge.setChosen(true);
            eventListener.OnClick(this, edge);
        } else
            Log.d(this.getClass().getName(), "Edge already chosen by a player");
    }

    public boolean isComplete(){
        boolean isComplete = true;

        for(Edge e : edges.values()){
            if(!e.isChosen())
                isComplete = false;
        }

        return isComplete;
    }

}
