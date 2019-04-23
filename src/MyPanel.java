import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class MyPanel extends JPanel {

    ArrayList<Rectangle2D> snake;
    Ellipse2D apple;


    MyPanel() {
        snake = new ArrayList<>();
        apple = new Ellipse2D.Double(0, 0, 0, 0);
        setPreferredSize(new Dimension(600, 600));
    }

    public void updateSnake(ArrayList<Rectangle2D> newSnake) {
        snake = newSnake;
    }

    public void updateApple(Ellipse2D newApple) {
        apple = newApple;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.LIGHT_GRAY);
        //draw frame
        Line2D line = new Line2D.Double(0, 0, 599, 0);
        g2d.draw(line);
        line = new Line2D.Double(599, 0, 599, 599);
        g2d.draw(line);
        line = new Line2D.Double(599, 599, 0, 599);
        g2d.draw(line);
        line = new Line2D.Double(0, 599, 0, 0);
        g2d.draw(line);
        g2d.setColor(Color.BLUE);
        for (Rectangle2D rect :
                snake) {
            g2d.draw(rect);
            g2d.setColor(Color.BLACK);
        }
        g2d.setColor(Color.RED);
        g2d.draw(apple);

    }

    public void drawRectAt(int x, int y, Dimension size) {
        Rectangle2D rect = new Rectangle2D.Double(x, y, size.width, size.height);
        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.draw(rect);
    }


}
