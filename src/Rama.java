import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Rama extends JFrame {

    int score = 0;
    int scale = 40;
    int width = 160 * 8, height = 90 * 8;
    Direction currentDirection;
    MyPanel panel;
    Rectangle2D head;
    LinkedList<Rectangle2D> tail;
    ArrayList<Rectangle2D> allSnake;
    Ellipse2D apple;
    Point2D lastLocation;
    JLabel label;
    boolean gameOver = false;

    public Rama() {
        super("Hello World");
        setSize(new Dimension(width, height));
        FlowLayout activeLayout = new FlowLayout();

        setLayout(activeLayout);
        panel = new MyPanel();
        add(panel);

        label = new JLabel();
        label.setText("Hej to ja");
        add(label);


        //add(button);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                switch (code) {
                    case KeyEvent.VK_UP:
                        currentDirection = Direction.UP;
                        System.out.println("pressed up");
                        break;
                    case KeyEvent.VK_DOWN:
                        currentDirection = Direction.DOWN;
                        System.out.println("pressed down");
                        break;
                    case KeyEvent.VK_LEFT:
                        currentDirection = Direction.LEFT;
                        System.out.println("pressed left");
                        break;
                    case KeyEvent.VK_RIGHT:
                        currentDirection = Direction.RIGHT;
                        System.out.println("pressed right");
                        break;
                    case KeyEvent.VK_R:
                        reset();
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        Thread newThread;
        newThread = new Thread() {
            public void run() {
                try {

                    Thread.sleep(1000);
                    play();

                } catch (InterruptedException v) {
                    System.out.println(v);
                }
            }
        };
        newThread.start();


    }

    public void reset() {
        currentDirection = Direction.RIGHT;
        head = new Rectangle2D.Double(0, 0, scale, scale);
        tail = new LinkedList<>();
        allSnake = new ArrayList<>();
        updateAllSnake();
        spawnApple();
        score = 0;
    }

    public void updateAllSnake() {
        allSnake.clear();
        allSnake.add(head);
        allSnake.addAll(tail);
    }

    public void updateLabel() {
        label.setText("Score: " + score);
    }


    public void moveTail() {
        if (tail.size() == 0) {

        } else if (tail.size() == 1) {
            tail.get(0).setRect(lastLocation.getX(), lastLocation.getY(),
                    getHeadSize().width, getHeadSize().getHeight());
        } else {
            Rectangle2D tmp = tail.removeLast();
            tmp.setRect(lastLocation.getX(), lastLocation.getY(),
                    getHeadSize().width, getHeadSize().getHeight());
            tail.addFirst(tmp);
        }

    }

    void spawnApple() {
        boolean ok = false;
        Random rand = new Random();
        int valX = 0;
        int valY = 0;
        while (!ok) {

            valX = rand.nextInt(600 / scale);
            valY = rand.nextInt(600 / scale);
            if (!isOccupied(valX * scale, valY * scale)) {
                ok = true;
            }
        }

        apple = new Ellipse2D.Double(valX * scale, valY * scale, scale, scale);

    }

    public boolean isOccupied(int x, int y) {
        if (!tail.isEmpty()) {
            for (Rectangle2D tailElem :
                    tail) {
                if (tailElem.intersects(x, y, getHeadSize().width, getHeadSize().height)) {
                    return true;
                }
            }
        }
        if (head != null) {
            return head.intersects(x, y, getHeadSize().width, getHeadSize().height);
        }
        return false;
    }

    void eatenApple() {
        score++;
        updateLabel();
        addTail();
        apple = null;
        spawnApple();
    }

    public void play() {
        reset();
        panel.repaint();

        while (!gameOver) {

            moveSnake();
            updateAllSnake();
            checkDeath();
            checkEaten();
            panel.updateApple(apple);
            panel.updateSnake(allSnake);
            try {
                Thread.sleep(300);
                panel.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        label.setText("GAME OVER Final Score:" + score);
    }

    private void checkDeath() {
        for (Rectangle2D tailElem :
                tail) {
            if (tailElem.intersects(head)) {
                gameOver();
            }
        }
    }

    private void gameOver() {
        gameOver = true;
    }

    private void checkEaten() {
        if (head.intersects(apple.getX(), apple.getY(), apple.getWidth(), apple.getHeight())) {
            eatenApple();
        }
    }

    public void addTail() {
        Rectangle2D rect;
        //if it's the first one
        if (tail.size() == 0) {
            rect = new Rectangle2D.Double(lastLocation.getX(), lastLocation.getY(),
                    getHeadSize().width, getHeadSize().height);
        } else {
            rect = new Rectangle2D.Double(tail.getLast().getX(), tail.getLast().getY(),
                    getHeadSize().width, getHeadSize().height);
        }

        tail.add(rect);
    }

    public Direction getDirection() {
        return currentDirection;
    }

    public void moveSnake() {

        lastLocation = new Point2D.Double(getHeadLocation().getX(), getHeadLocation().getY());

        switch (currentDirection) {
            case RIGHT:
                moveSnakeRight();
                break;
            case LEFT:
                moveSnakeLeft();
                break;
            case DOWN:
                moveSnakeDown();
                break;
            case UP:
                moveSnakeUp();
                break;
        }
        moveTail();
    }

    private void moveSnakeRight() {
        double nextX = getHeadLocation().getX() + getHeadSize().getWidth();
        double nextY = getHeadLocation().getY();

        head.setRect(nextX, nextY, head.getWidth(), head.getHeight());

    }

    private void moveSnakeLeft() {
        double nextX = getHeadLocation().getX() - getHeadSize().getWidth();
        double nextY = getHeadLocation().getY();

        head.setRect(nextX, nextY, head.getWidth(), head.getHeight());
    }

    private void moveSnakeDown() {
        double nextX = getHeadLocation().getX();
        double nextY = getHeadLocation().getY() + getHeadSize().getHeight();

        head.setRect(nextX, nextY, head.getWidth(), head.getHeight());
    }

    private void moveSnakeUp() {
        double nextX = getHeadLocation().getX();
        double nextY = getHeadLocation().getY() - getHeadSize().getHeight();

        head.setRect(nextX, nextY, head.getWidth(), head.getHeight());
    }

    private Point2D getHeadLocation() {
        return new Point2D.Double(head.getX(), head.getY());
    }

    Dimension getHeadSize() {
        return new Dimension((int) head.getWidth(), (int) head.getHeight());
    }


}
