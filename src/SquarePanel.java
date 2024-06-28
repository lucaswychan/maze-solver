import java.awt.*;
import javax.swing.JPanel;
import java.io.Serial;

/**
 * The SquarePanel class is a custom JPanel that displays
 * a square panel with a specified background color.
 */
public class SquarePanel extends JPanel{

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *Constructs a SquarePanel object with
     * the specified background color.
     * @param color the color to set as the background of the panel
     */
    public SquarePanel(Color color){
        this.setBackground(color);
    }

    /**
     * Changes the background color of the panel
     * and triggers a repaint.
     * @param color -the color to set as the new background of the panel
     */
    public void ChangeColor(Color color){
        this.setBackground(color);
        this.repaint();
    }

}