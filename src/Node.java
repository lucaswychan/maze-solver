

/**
 * For A* implementation only
 * Store the distance between the target vertex and the current vertex for min heap comparison
 */
public class Node implements Comparable<Node> {
    int[] vertex;
    int distance;

    public Node(int[] vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.distance, other.distance);
    }
}
