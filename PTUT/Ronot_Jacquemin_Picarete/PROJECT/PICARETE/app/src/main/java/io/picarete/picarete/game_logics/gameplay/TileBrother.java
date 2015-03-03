package io.picarete.picarete.game_logics.gameplay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileBrother extends Tile {
    private Map<ETileSide, Tile> brothers = null;

    public TileBrother(int id, Edge left, Edge top, Edge right, Edge bottom){
        super(id, left, top, right, bottom);

        brothers = new HashMap<>();
    }

    public void linkBrothers(Tile leftTile, Tile topTile, Tile rightTile, Tile bottomTile){
        if(leftTile != null)
            brothers.put(ETileSide.LEFT, leftTile);
        if(topTile != null)
            brothers.put(ETileSide.TOP, topTile);
        if(rightTile != null)
            brothers.put(ETileSide.RIGHT, rightTile);
        if(bottomTile != null)
            brothers.put(ETileSide.BOTTOM, bottomTile);
    }

    public List<Tile> getScoreBrothers(List<Tile> tilesBrothers){
        if(isComplete()){
            tilesBrothers.add(this);
            for(Tile t : brothers.values()){
                if(!tilesBrothers.contains(t) && t.isComplete() && t.getIdPlayer() == this.getIdPlayer() && t instanceof TileBrother){
                    tilesBrothers = ((TileBrother) t).getScoreBrothers(tilesBrothers);
                }
            }
        }

        return tilesBrothers;
    }

    public Map<ETileSide, Tile> getBrothers() {
        return brothers;
    }
}
