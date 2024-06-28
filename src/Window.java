import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * The Window class extends JFrame and
 * represents the main window of the game.
 */
public class Window extends JFrame {
    public static ArrayList<ArrayList<DataOfSquare>> Grid;
    private static final int width = 30;
    private static final int height = 30;
    public static int entryRow = -1;
    public static int entryCol = -1;
    public static int exitRow = -1;
    public static int exitCol = -1;
    ArrayList<ArrayList<DataOfSquare>> Squares = new ArrayList<>();
    private final Random rand = new Random();

    /**
     * Default Constructor
     */
    public Window() {
    }

    /**
     * Constructs a Window object with the provided 2D array of
     * integer graph data (mazeMap)
     *
     * @param graph - the 2D integer array representing the graph data
     */
    public Window(int[][] graph) {

        Grid = new ArrayList<>(width);

        pickUpEntryPoint(graph);

        for (int i = 0; i < height; i++) {
            ArrayList<DataOfSquare> data = new ArrayList<>(width);
            for (int j = 0; j < width; j++) {
                int color = 0;
                if ((i == entryRow && j == entryCol)) {
                    graph[i][j] = 0;
                    color = 2;
                } else if ((i == exitRow && j == exitCol)) {
                    graph[i][j] = 0;
                    //set the entry and exit color to green
                    color = 3;
                } else if (graph[i][j] == 1) {
                    color = 1;
                }
                DataOfSquare c = new DataOfSquare(color);
                data.add(c);
            }
            Grid.add(data);
        }

        getContentPane().setLayout(new GridLayout(30, 30, 0, 0));

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                getContentPane().add(Grid.get(i).get(j).square);
            }
        }
        Squares = Window.Grid;
    }

    /**
     * Picks up the entry point on the border
     * of the graph based on a randomly selected side.
     *
     * @param graph - the 2D integer array representing the graph data
     */
    private void pickUpEntryPoint(int[][] graph) {
        int borderSide = rand.nextInt(3); // left or top or bottom
        checkEntryBorderSide(borderSide);
        while (true) {
            if (borderSide == 0 && graph[entryRow][entryCol + 1] == 0) { //right
                break;
            } else if (borderSide == 1 && graph[entryRow + 1][entryCol] == 0) { //top
                break;
            } else if (borderSide == 2 && graph[entryRow - 1][entryCol] == 0) { // bottom
                break;
            }
            checkEntryBorderSide(borderSide);
        }
        pickUpExitPoint(graph, borderSide);
    }

    /**
     * Checks and sets the coordinates of the
     * entry point based on the specified border side.
     *
     * @param borderSide - the selected border side for the entry point
     *                   (0: left, 1: top, 2: bottom)
     */
    private void checkEntryBorderSide(int borderSide) {
        if (borderSide == 0) { //left
            entryRow = rand.nextInt(height);
            entryCol = 0;
        } else if (borderSide == 1) { //top
            entryRow = 0;
            entryCol = rand.nextInt(width / 2 - 1);
        } else { // bottom
            entryRow = 29;
            entryCol = rand.nextInt(width / 2 - 1);
        }
    }

    /**
     * Picks up the exit point on the border of the graph
     * based on the entry border side.
     *
     * @param graph           -the 2D integer array representing the graph data
     * @param entryBroderSide -  entryBorderSide the selected border side for the entry point
     *                        (0: right, 1: top, 2: bottom)
     */
    private void pickUpExitPoint(int[][] graph, int entryBroderSide) {
        int borderSide = 0;
        if (entryBroderSide == 1) {
            borderSide = 2;
        } else if (entryBroderSide == 2) {
            borderSide = 1;
        }
        checkExitBorderSide(borderSide);
        while (true) {
            if (borderSide == 0 && graph[exitRow][exitCol - 1] == 0) { //right
                break;
            } else if (borderSide == 1 && graph[exitRow + 1][exitCol] == 0) { //top
                break;
            } else if (borderSide == 2 && graph[exitRow - 1][exitCol] == 0) { // bottom
                break;
            } else {
                checkExitBorderSide(borderSide);
            }
        }
    }

    /**
     * Checks and sets the coordinates of the exit point
     * based on the specified border side.
     *
     * @param borderSide - borderSide the selected border side for the exit point
     *                   (0: right 1: top, 2: bottom 3:)
     */
    private void checkExitBorderSide(int borderSide) {
        if (borderSide == 0) { //right
            exitRow = rand.nextInt(height);
            exitCol = 29;
        } else if (borderSide == 1) { //top
            exitRow = 0;
            exitCol = rand.nextInt(width / 2 - 1) + width / 2 + 1;
        } else if (borderSide == 2) { //bottom
            exitRow = 29;
            exitCol = rand.nextInt(width / 2 - 1) + width / 2 + 1;
        }
    }
}

