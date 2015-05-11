package os.io.geolocos.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import os.io.geolocos.Container.Coordinate;

/**
 * Created by iem on 11/05/15.
 */
public class Path {

    public List<Line> getPathFromPoints(List<Coordinate> points, int seed){
        List<Line> path = new ArrayList();

        if(points == null || points.size() < 1)
            return path;

        boolean allConnected = false;
        int count = 0;
        do {
            count++;
            Coordinate p1 = points.get(new Random(seed + count).nextInt(points.size()));
            Coordinate p2 = points.get(new Random((seed + count) * 1000).nextInt(points.size()));

            if(p1 != p2){
                boolean alreadyExist = false;
                for (Line l : path){
                    if((l.p1 == p1 || l.p2 == p1) && (l.p1 == p2 || l.p2 == p2))
                        alreadyExist = true;
                }
                if(!alreadyExist)
                    path.add(new Line(p1, p2, 1f));
            }

            allConnected = true;
            for (Coordinate point : points){
                boolean pointFind = false;
                for (Line line : path){
                    if(line.p1 == point || line.p2 == point)
                        pointFind = true;
                }

                if(!pointFind)
                    allConnected = false;
            }


        }while(!allConnected);

        return path;
    }

    public LinkedList<Coordinate> getPathBetweenPoints(List<Coordinate> coordinates, Coordinate p1, Coordinate p2, List<Line> adjacence){
        LinkedList<Coordinate> path = new LinkedList<>();

        Map<String, Node> nodes = new HashMap<>();
        for (Coordinate coordinate : coordinates){
            nodes.put(coordinate.getId(), new Node(coordinate.getId()));
        }

        List<Node> settledNodes = new ArrayList<>();
        List<Node> unSettledNodes = new ArrayList<>();

        unSettledNodes.add(nodes.get(p1.getId()));
        nodes.get(p1.getId()).distance = 0f;

        while(unSettledNodes.size() != 0){
            Node node = nodes.get(getNodeWithLowestDistance(unSettledNodes));
            unSettledNodes.remove(node);
            settledNodes.add(node);
            unSettledNodes = evaluateNeighbors(node, settledNodes, unSettledNodes, adjacence, nodes);
        }

        Node pathCursor = nodes.get(p2.getId());
        path.add(findCoordinate(pathCursor.id, coordinates));
        do {
            pathCursor = pathCursor.parent;
            path.add(findCoordinate(pathCursor.id, coordinates));
        }while(pathCursor.parent != null );

        return path;
    }

    public String getNodeWithLowestDistance(List<Node> unSettledNodes){
        float minValue = Float.MAX_VALUE;
        String minId = "";

        for (Node node : unSettledNodes){
            if(node.distance < minValue){
                minValue = node.distance;
                minId = node.id;
            }
        }

        return minId;
    }

    public List<Node> evaluateNeighbors(Node evaluationNode, List<Node> settledNodes, List<Node> unSettledNodes, List<Line> adjacence, Map<String, Node> nodes){
        List<Node> neighbors = new ArrayList<>();

        for (Line l : adjacence){
            if(l.p1.getId() == evaluationNode.id){
                neighbors.add(nodes.get(l.p2.getId()));
            } else if(l.p2.getId() == evaluationNode.id){
                neighbors.add(nodes.get(l.p1.getId()));
            }
        }

        for (Node n : neighbors){
            if(!settledNodes.contains(n)){
                Line line = findLink(evaluationNode, n, adjacence);
                float newDistance = evaluationNode.distance + line.weight;
                if(n.distance > newDistance){
                    n.parent = evaluationNode;
                    n.distance = newDistance;
                    unSettledNodes.add(n);
                }
            }
        }

        return unSettledNodes;
    }

    private Line findLink(Node node1, Node node2, List<Line> adjacence) {
        Line line = null;

        for (Line l : adjacence){
            if((l.p1.getId() == node1.id && l.p2.getId() == node2.id) || (l.p1.getId() == node2.id && l.p2.getId() == node1.id))
                line = l;
        }

        return line;
    }

    private Coordinate findCoordinate(String id, List<Coordinate> coordinates) {
        Coordinate coordinate = null;

        for (Coordinate c : coordinates){
            if(c.getId() == id)
                coordinate = c;
        }

        return coordinate;
    }
}
