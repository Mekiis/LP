package os.io.geolocos.Path;

/**
 * Created by iem on 11/05/15.
 */
public class Node {
    public float distance = Float.MAX_VALUE;
    public String id = "";
    public Node parent = null;

    public Node(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        if (Float.compare(node.distance, distance) != 0) return false;
        if (!id.equals(node.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (distance != +0.0f ? Float.floatToIntBits(distance) : 0);
        result = 31 * result + id.hashCode();
        return result;
    }
}
