package io.picarete.picarete.game_logics.ia;

import android.content.Context;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import io.picarete.picarete.game_logics.Game;
import io.picarete.picarete.game_logics.gameplay.ETileSide;
import io.picarete.picarete.game_logics.gameplay.Edge;
import io.picarete.picarete.game_logics.gameplay.Tile;
import io.picarete.picarete.model.NoDuplicatesList;

/**
 * Created by root on 1/7/15.
 */
public class MCTSIA extends AIA {

    @Override
    protected Edge findEdge(int height, int width, Game game, List<Edge> previousEdgesPlayed) {
        Edge bestEdge = null;

        Log.d(this.getName(), "MCTS");

        TreeNodeMCTS nodeRoot = new TreeNodeMCTS(previousEdgesPlayed.get(previousEdgesPlayed.size()-1).id);
        for(int i = 0; i < 100; i++){
            Game gameCopied = copyGame(game);
            gameCopied.idPlayer = 1;
            nodeRoot.selectAction(gameCopied);
        }

        TreeNodeMCTS bestNode = nodeRoot.selectBestEdge();
        for (Tile t : game.getTiles()){
            for (Edge e : t.getEdges().values()){
                if(e.id == bestNode.idEdge)
                    bestEdge = e;
            }
        }

        return bestEdge;
    }

    private Game copyGame(Game game) {
        Game gameCopied = new Game(game.context, game.gameMode, game.height, game.width, 0);

        gameCopied.scores.put(0, game.getScores().get(0));
        gameCopied.scores.put(1, game.getScores().get(1));

        for (Tile t : game.getTiles()){
            gameCopied.tiles.add(createBase(t, gameCopied.getTiles(), gameCopied));
        }

        return gameCopied;
    }

    private Tile copyTile(Tile tile, List<Tile> tiles, Game game) {
        Edge edgeLeft = null;
        Edge edgeTop = null;
        Edge edgeRight = null;
        Edge edgeBottom = null;


        for(Tile t : tiles){
            for (Edge e : t.getEdges().values()){
                if(e.id == tile.getEdges().get(ETileSide.LEFT).id)
                    edgeLeft = e;
                if(e.id == tile.getEdges().get(ETileSide.TOP).id)
                    edgeTop = e;
                if(e.id == tile.getEdges().get(ETileSide.RIGHT).id)
                    edgeRight = e;
                if(e.id == tile.getEdges().get(ETileSide.BOTTOM).id)
                    edgeBottom = e;
            }
        }

        if(edgeLeft == null)
            edgeLeft = new Edge(tile.getEdges().get(ETileSide.LEFT).id);
        if(edgeTop == null)
            edgeTop = new Edge(tile.getEdges().get(ETileSide.TOP).id);
        if(edgeRight == null)
            edgeRight = new Edge(tile.getEdges().get(ETileSide.RIGHT).id);
        if(edgeBottom == null)
            edgeBottom = new Edge(tile.getEdges().get(ETileSide.BOTTOM).id);

        edgeBottom.setIdPlayer(tile.getEdges().get(ETileSide.BOTTOM).getIdPlayer());
        edgeRight.setIdPlayer(tile.getEdges().get(ETileSide.RIGHT).getIdPlayer());
        edgeTop.setIdPlayer(tile.getEdges().get(ETileSide.TOP).getIdPlayer());
        edgeLeft.setIdPlayer(tile.getEdges().get(ETileSide.LEFT).getIdPlayer());

        Tile tileCopied = new Tile(tile.id, edgeLeft, edgeTop, edgeRight, edgeBottom);
        tileCopied.setEventListener(game);
        tileCopied.setIdPlayer(tileCopied.getIdPlayer());

        return tileCopied;
    }

    private Tile createBase(Tile tile, List<Tile> tiles, Game game){
        Edge left;
        Edge top;
        Edge right = new Edge(tile.getEdges().get(ETileSide.RIGHT).id);
        right.setIdPlayer(tile.getEdges().get(ETileSide.RIGHT).getIdPlayer());
        Edge bottom = new Edge(tile.getEdges().get(ETileSide.BOTTOM).id);
        bottom.setIdPlayer(tile.getEdges().get(ETileSide.BOTTOM).getIdPlayer());
        if(tile.row == 0){
            top = new Edge(tile.getEdges().get(ETileSide.TOP).id);
            top.setIdPlayer(tile.getEdges().get(ETileSide.TOP).getIdPlayer());
        } else {
            top = tiles.get((tile.row-1)*game.width+tile.col).getEdges().get(ETileSide.BOTTOM);
        }

        if(tile.col == 0){
            left = new Edge(tile.getEdges().get(ETileSide.LEFT).id);
            left.setIdPlayer(tile.getEdges().get(ETileSide.LEFT).getIdPlayer());
        } else {
            left = tiles.get(tile.row*game.width+tile.col-1).getEdges().get(ETileSide.RIGHT);
        }

        Tile tileCopied = new Tile(tile.id, left, top, right, bottom);
        tileCopied.row = tile.row;
        tileCopied.col = tile.col;
        tileCopied.setEventListener(game);

        return tileCopied;
    }

    public class TreeNodeMCTS {
        int idEdge = -1;
        List<TreeNodeMCTS> children = new NoDuplicatesList<>();
        Random r = new Random();
        double epsilon = 1e-6;

        double nVisits = 0, totValue = 0;

        public TreeNodeMCTS(int idEdge){
            this.idEdge = idEdge;
        }

        public int selectAction(Game game) {
            int score;

            game = playEdge(game, idEdge);

            if(this.isLeaf()){
                this.expand(game);
                TreeNodeMCTS e = selectEdgeToPlay();
                score = e.play(game);
            } else {
                TreeNodeMCTS e = selectBestEdge();
                Log.d(this.getClass().getName(), ""+e.idEdge);
                score = e.selectAction(game);
            }

            updateStats(score);

            return score;
        }

        private int play(Game game) {
            int score = -1;

            game = playEdge(game, idEdge);

            if(!isFinish(game)){
                this.expand(game);
                TreeNodeMCTS e = selectEdgeToPlay();
                score = e.play(game);
            } else {
                int scoreP1 = game.getScores().get(0);
                int scoreP2 = game.getScores().get(1);

                if(scoreP1 >= scoreP2) score = 0;
                else score = 1;
            }

            updateStats(score);

            return score;
        }

        private Game playEdge(Game game, int idEdge){
            for (Tile t : game.getTiles()){
                for (Edge e : t.getEdges().values()){
                    if(e.id == idEdge){
                        t.onClick(e);
                        return game;
                    }
                }
            }

            return game;
        }

        private boolean isFinish(Game game) {
            boolean isFinished = true;

            for (Tile t : game.getTiles())
                if(!t.isComplete())
                    isFinished = false;

            return isFinished;
        }

        private void expand(Game game) {
            for (int i = 0; i < game.getTiles().size(); i++) {
                for (Edge e : game.getTiles().get(i).getEdges().values()){

                    if(!e.isChosen()){
                        boolean isAlready = false;
                        for (TreeNodeMCTS n : children){
                            if(n.idEdge == e.id )
                                isAlready = true;
                        }
                        if(!isAlready)
                            children.add(new TreeNodeMCTS(e.id));
                    }
                }
            }
        }

        private void updateStats(double value) {
            nVisits++;
            totValue += value;
        }

        private boolean isLeaf() {
            return (children.size() == 0 ? true : false);
        }

        private TreeNodeMCTS selectBestEdge() {
            TreeNodeMCTS selected = null;
            double bestValue = Double.MIN_VALUE;
            for (TreeNodeMCTS c : children) {
                double uctValue = c.totValue / (c.nVisits + epsilon) +
                        Math.sqrt(Math.log(nVisits+1) / (c.nVisits + epsilon)) +
                        r.nextDouble() * epsilon;
                // small random number to break ties randomly in unexpanded nodes
                if (uctValue > bestValue) {
                    selected = c;
                    bestValue = uctValue;
                }
            }

            Log.d(this.getClass().getName(), ""+selected.idEdge+" with value "+bestValue);
            return selected;
        }

        private TreeNodeMCTS selectEdgeToPlay() {
            return children.get(r.nextInt(children.size()));
        }

    }
}
