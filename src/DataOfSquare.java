import java.util.ArrayList;
import java.awt.Color;

/**
 * The DataOfSquare class represents the
 * data associated with a square in a grid.
 */
public class DataOfSquare  {
    ArrayList<Color> ColorList = new ArrayList<Color>();
    public int color; //2:EntryPoint 1: Block, 0:Empty
    public SquarePanel square;

    /**
     * Constructs a DataOfSquare object with the specified color.
     * @param color -the initial color state of the square
     */
    public DataOfSquare(int color){
        ColorList.add(Color.white);    //0 clear vertex
        ColorList.add(Color.GRAY);    //1  wall
        ColorList.add(Color.green);    //2  entry
        ColorList.add(Color.orange);    //3
        ColorList.add(Color.yellow);  // 4 exit
        ColorList.add(Color.cyan);    //5
        ColorList.add(Color.magenta);  //6
        ColorList.add(Color.red);       //7  jerry
        ColorList.add(Color.pink);      //8  tom

        if (color < 0 || color >= ColorList.size()) {
            color = 0;
        }

        this.color=color;
        square = new SquarePanel(ColorList.get(color));
    }

    /**
     * Draws the square with the specified color.
     * @param changeColor -  the color state to set for the square
     * @see SquarePanel
     */
    public void drawVertex(int changeColor){
        if (changeColor < 0 || changeColor >= ColorList.size()) {
            changeColor = 0;
        }
        color = changeColor;
        square.ChangeColor(ColorList.get(changeColor));
    }
}
