import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class MyPanel extends JPanel {

    ArrayList<Rectangle2D> snake;
    ArrayList<Ellipse2D> apples;


    MyPanel() {
        snake = new ArrayList<>();
        apples = new ArrayList<>();
        setPreferredSize(new Dimension(600, 600));
    }

    public void updateSnake(ArrayList<Rectangle2D> newSnake) {
        snake = newSnake;
    }

    public void updateApples(ArrayList<Ellipse2D> newApples) {
        apples = newApples;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Rectangle2D rect :
                snake) {
            g2d.draw(rect);
        }

        for (Ellipse2D apple :
                apples) {
            g2d.draw(apple);
        }

    }

    public void drawRectAt(int x, int y, Dimension size) {
        Rectangle2D rect = new Rectangle2D.Double(x, y, size.width, size.height);
        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.draw(rect);
    }


    public void drawAllSnake(Rectangle2D head, ArrayList<Rectangle2D> snake) {
        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.draw(head);
        for (Rectangle2D square :
                snake) {
            g2d.draw(square);
        }
    }


}
