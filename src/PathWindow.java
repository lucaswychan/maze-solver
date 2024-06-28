// Remove the invalid import statement

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PathWindow extends Window {

    private final MazeSPT spt;
    private final int mazeSize;
    private final int[] start;
    private final int[] end;
    private final List<int[]> shortestPath;
    public final List<List<int[]>> possiblePath;
    private final List<Integer> possiblePathDisplayIndexList = new ArrayList<>();

    /**
     * Constructor 1
     *
     * @param graph the graph for displaying the paths between entry and exit
     */
    public PathWindow(int[][] graph) {
        super(graph);
        spt = new MazeSPT(graph);
        mazeSize = spt.getMazeSize();
        spt.setAlgorithm("DFS");
        this.start = new int[]{Window.entryRow, Window.entryCol};
        this.end = new int[]{Window.exitRow, Window.exitCol};
        shortestPath = spt.findShortestPath(this.start, this.end);
        possiblePath = spt.possiblePath;
    }

    /**
     * Constructor 2
     *
     * @param graph the graph for displaying the paths between entry and exit
     * @param start the fixed entry point
     * @param end the fixed exit point
     */
    public PathWindow(int[][] graph, int[] start, int[] end) {
        super(graph);
        spt = new MazeSPT(graph);
        mazeSize = spt.getMazeSize();
        spt.setAlgorithm("DFS");
        this.start = start;
        this.end = end;
        shortestPath = spt.findShortestPath(this.start, this.end);
        possiblePath = spt.possiblePath;
        int[] randomStart = new int[] {Window.entryRow, Window.entryCol};
        int[] randomEnd = new int[]{Window.exitRow, Window.exitCol};
        Window.Grid.get(randomStart[0]).get(randomStart[1]).drawVertex(1);
        Window.Grid.get(randomEnd[0]).get(randomEnd[1]).drawVertex(1);
    }

    /**
     * Display the shortest path or possible paths in the pop-up window
     *
     * @param displayPossiblePath to determine whether display the possible paths. If false, only shortest path will be displayed
     */
    public void displayPath(boolean displayPossiblePath) {

        // this.dispose();
        this.setTitle("Tom & Jerry Run and Catch");
        this.setSize(300, 300);
        this.setVisible(true);
        // this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.possiblePathDisplayIndexList.clear();

        // Possible Path Display
        if (!possiblePath.isEmpty() && displayPossiblePath) {
            boolean[][] displayed = new boolean[mazeSize][mazeSize];
            int color = 4, orange = 3, green = 2;
            boolean overlap = true;

            int size = possiblePath.size();
            int maxPath = Math.min(5, size);
            System.out.println("There are " + size + " possible paths can be displayed. ");
            Set<Integer> selectedIndices = new HashSet<>();
            Random random = new Random();
            while (selectedIndices.size() < maxPath) {
                int randomIndex = random.nextInt(size);
                // if (possiblePath.get(randomIndex).size() == shortestPath.size()) continue;  // get rid of the shortest path in the display of possible path
                selectedIndices.add(randomIndex);
            }

            possiblePathDisplayIndexList.addAll(selectedIndices);

            for (int index : selectedIndices) {
                List<int[]> path = possiblePath.get(index);

                for (int j = 1; j < path.size(); j++) {
                    if (!displayed[path.get(j)[0]][path.get(j)[1]]) {
                        displayed[path.get(j)[0]][path.get(j)[1]] = true;
                        Window.Grid.get(path.get(j)[0]).get(path.get(j)[1]).drawVertex(color);
                        if (overlap && j != 1) {
                            Window.Grid.get(path.get(j - 1)[0]).get(path.get(j - 1)[1]).drawVertex(orange);  // orange
                            overlap = false;
                        }
                    } else if (!overlap) {
                        overlap = true;
                        Window.Grid.get(path.get(j)[0]).get(path.get(j)[1]).drawVertex(orange);
                    }
                }
                color++;
            }
            Window.Grid.get(this.possiblePath.get(0).get(0)[0]).get(this.possiblePath.get(0).get(0)[1]).drawVertex(green);
        } else {
            // Shortest Path Display
            for (int[] vertex : shortestPath) {
                Window.Grid.get(vertex[0]).get(vertex[1]).drawVertex(2);
            }
        }
        // write into the CSV file
        this.writeCSV();
    }

    /**
     * Write the path data into the path.cs  file with data [Path name (SP/OP), Color of the path, all vertices, Length]
     * It will be automatically invoked if displaying path is requested. Whereas it can be called invoked out of the class.
     */
    public void writeCSV() {
        String fileName = "path.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // shortest path
            if (!shortestPath.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                sb.append("[").append("SP").append(1).append(", ");
                for (int[] vertex : shortestPath) {
                    String coordinate = "(" + vertex[0] + ".." + vertex[1] + "), ";
                    sb.append(coordinate);
                }
                sb.append("Length = ").append(shortestPath.size()).append(" ]");
                writer.write(sb.toString());
                writer.newLine();
                writer.newLine();
            }
            // possible path
            if (!possiblePath.isEmpty()) {
                int pathNum = 1;
                String[] colors = {"Yellow", "Cyan", "Magenta", "Red", "Pink"};
                if (!possiblePathDisplayIndexList.isEmpty()) {
                    // Only write the selected paths (which are the paths to be displayed in the GUI) into the CSV file
                    // System.out.println("I am now in the writeCSV with possiblePathDisplayIndexList - " + possiblePath.size() + "  -  " + possiblePathDisplayIndexList.size());
                    for (int i = 0; i < possiblePathDisplayIndexList.size(); i++) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("[").append("OP").append(pathNum++).append(", ");
                        sb.append(colors[i]).append(", ");
                        List<int[]> selectedPossiblePath = possiblePath.get(possiblePathDisplayIndexList.get(i));
                        for (int[] displayedpath : selectedPossiblePath) {
                            String coordinate = "(" + displayedpath[0] + ".." + displayedpath[1] + "), ";
                            sb.append(coordinate);
                        }
                        sb.append("Length = ").append(selectedPossiblePath.size()).append(" ]");
                        writer.write(sb.toString());
                        writer.newLine();
                        writer.newLine();
                    }
                } else { // write all possible path into the CSV file
                    for (List<int[]> path : possiblePath) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("[").append("OP").append(pathNum++).append(", ");
                        for (int[] vertex : path) {
                            String coordinate = "(" + vertex[0] + ".." + vertex[1] + "), ";
                            sb.append(coordinate);
                        }
                        sb.append("Length = ").append(path.size()).append(" ]");
                        writer.write(sb.toString());
                        writer.newLine();
                        writer.newLine();
                    }
                }
            }
        } catch (IOException ignored) {
        } finally {
            System.out.println("Writing completed");
        }
    }

    public List<int[]> getShortestPath() {
        return this.shortestPath;
    }
}
