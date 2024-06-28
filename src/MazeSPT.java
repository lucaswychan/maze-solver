import java.util.*;

/**
 * @author Lucas Chan
 *
 * <p>The main class for finding the shortest path, with enabling algorithm DFS, BFS and A*</p>
 * <p>The default algorithm is A* as it performs the best</p>
 */
public class MazeSPT {
    private final int[][] graph;        // graph containing only 0 and 1 for path traversal
    private final List<int[]> path = new ArrayList<>();
    public final List<List<int[]>> possiblePath = new ArrayList<>();
    private final int mazeSize;
    private final List<String> allAlgorithm = new ArrayList<>(Arrays.asList("BFS", "DFS", "A*"));
    private List<int[]> shortestPath = new ArrayList<>();   // storing the shortest path
    private boolean[][] visited;
    private int shortestLength;         // shortest path length of the graph
    private String algorithm = "A*";   // selected algorithm for path traversal. Options: DFS, BFS, A*

    /**
     * Constructor
     *
     * @param graph the graph for finding the paths between two vertices
     */
    public MazeSPT(int[][] graph) {    // Constructor
        this.graph = graph;
        this.mazeSize = graph.length;
        this.shortestLength = mazeSize * mazeSize;
    }

    /**
     * Depth First Search algorithm to find the shortest path and all possible paths
     * It's the only algorithm with capability to get all shortest paths
     *
     * @param currVertex the current visiting vertex
     * @param end        end vertex
     */
    private void dfs(int[] currVertex, int[] end) {
        if (Arrays.equals(currVertex, end)) {
            possiblePath.add(new ArrayList<>(path));
            if (path.size() < shortestLength) {
                shortestPath = new ArrayList<>(path);
                shortestLength = shortestPath.size();
            }
            return;
        }

        visited[currVertex[0]][currVertex[1]] = true;
        for (int[] vertex : getNeighbors(currVertex[0], currVertex[1])) {
            if (!visited[vertex[0]][vertex[1]]) {
                path.add(vertex);
                dfs(vertex, end);
                path.remove(path.size() - 1);
            }
        }
        visited[currVertex[0]][currVertex[1]] = false;
    }

    /**
     * A* algorithm to find the shortest path
     * Very similar to Dijkstra's algorithm with only difference of added heuristic
     *
     * @param start start vertex
     * @param end   end vertex
     */
    private void AStar(int[] start, int[] end) {
        int[] dist = new int[mazeSize * mazeSize];
        int[] parent = new int[mazeSize * mazeSize];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        dist[index(start)] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(start, 0));

        int[] currentVertex = null;

        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();
            currentVertex = currentNode.vertex;
            int currentIndex = index(currentVertex);

            if (visited[currentVertex[0]][currentVertex[1]]) continue;

            visited[currentVertex[0]][currentVertex[1]] = true;

            if (Arrays.equals(currentVertex, end)) break;

            for (int[] neighbor : getNeighbors(currentVertex[0], currentVertex[1])) {
                int newDist = dist[currentIndex] + 1;
                int neighborIndex = index(neighbor);

                if (newDist < dist[neighborIndex]) {
                    dist[neighborIndex] = newDist;
                    parent[neighborIndex] = currentIndex;
                    // add the heuristic into the comparison criteria (A* implementation)
                    pq.add(new Node(neighbor, newDist + Math.abs(neighbor[0] - end[0]) + Math.abs(neighbor[1] - end[1])));
                }
            }
        }

        if (!Arrays.equals(currentVertex, end)) {
            shortestLength = 0;
            return;
        }
        int current = index(end);
        while (current != -1) {
            shortestPath.add(coordinate(current));
            current = parent[current];
        }
        Collections.reverse(shortestPath);
        shortestLength = shortestPath.size();
    }

    /**
     * Breath First Search algorithm to find the shortest path
     *
     * @param start start vertex
     * @param end   end vertex
     */
    private void bfs(int[] start, int[] end) {
        Queue<int[]> q = new ArrayDeque<>();
        Map<int[], int[]> parent = new HashMap<>();
        int[] stop = {-1, -1};

        q.add(start);
        visited[start[0]][start[1]] = true;
        parent.put(start, stop);

        int[] current = {-1, -1};

        while (!q.isEmpty()) {
            current = q.remove();
            if (Arrays.equals(current, end)) break;

            for (int[] neighbor : getNeighbors(current[0], current[1])) {
                if (!visited[neighbor[0]][neighbor[1]]) {
                    visited[neighbor[0]][neighbor[1]] = true;
                    parent.put(neighbor, current);
                    q.add(neighbor);
                }
            }
        }

        if (!Arrays.equals(current, end)) {
            shortestLength = 0;
            return;
        }
        while (!Arrays.equals(current, stop)) {
            shortestPath.add(current);
            current = parent.get(current);
        }
        Collections.reverse(shortestPath);
        shortestLength = shortestPath.size();
    }

    /**
     * Find the shortest path between two vertices
     * Possible algorithm : DFS, BFS, A*
     *
     * @param start start vertex
     * @param end   end vertex
     * @return the found shortest path
     */
    public List<int[]> findShortestPath(int[] start, int[] end) {
        if (notValidInput(start) || notValidInput(end)) return new ArrayList<>();

        this.visited = new boolean[mazeSize][mazeSize];
        this.path.clear();
        this.shortestPath.clear();
        this.possiblePath.clear();
        this.shortestLength = mazeSize * mazeSize;

        switch (this.algorithm) {
            case "DFS":
                path.add(start);
                dfs(start, end);
                break;
            case "BFS":
                bfs(start, end);
                break;
            case "A*":
                AStar(start, end);
                break;
        }
        return this.shortestPath;
    }

    /**
     * Get all the near vertices of the give vertex
     * If the neighbor vertex is a wall, it will not be added to the return value
     *
     * @param row the located row of the selected vertex
     * @param col the located column of the selected vertex
     * @return the neighbors of the selected vertex
     */
    private List<int[]> getNeighbors(int row, int col) {
        List<int[]> neighbors = new ArrayList<>();

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (newRow >= 0 && newRow < this.graph.length && newCol >= 0 && newCol < this.graph[0].length && this.graph[newRow][newCol] == 0) {
                neighbors.add(new int[]{newRow, newCol});
            }
        }
        return neighbors;
    }

    /**
     * Only for A*
     *
     * @param vertex vertex in array representation
     * @return the vertex in 1D representation
     */
    private int index(int[] vertex) {
        return vertex[0] * mazeSize + vertex[1];
    }

    /**
     * Only for A*
     *
     * @param vertex vertex in 1D representation
     * @return the vertex in array representation
     */
    private int[] coordinate(int vertex) {
        int x = vertex / mazeSize;
        return new int[]{x, vertex - x * mazeSize};
    }

    /**
     *
     * @param input vertex
     * @return boolean value to see whether the input vertex is in correct array representation format
     */
    private boolean notValidInput(int[] input) {
        return (input == null) || (input.length != 2) || (input[0] < 0) || (input[0] >= mazeSize) || (input[1] < 0) || (input[1] >= mazeSize);
    }

    public void setAlgorithm(String algorithm) {
        if (allAlgorithm.contains(algorithm)) {
            this.algorithm = algorithm;
        }
    }

    public int getMazeSize() {
        return this.mazeSize;
    }

    public List<int[]> getShortestPath() {
        return this.shortestPath;
    }
}