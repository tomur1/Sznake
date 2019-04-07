import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    int scale = 100;
    int width = 160 * 6, height = 90 * 6;
    Direction currentDirection;
    MyPanel panel;
    Rectangle2D head;
    LinkedList<Rectangle2D> tail;
    ArrayList<Rectangle2D> allSnake;
    ArrayList<Ellipse2D> apples;

    public Rama() {
        super("Hello World");
        setSize(new Dimension(width, height));
        FlowLayout activeLayout = new FlowLayout();

        setLayout(activeLayout);
        panel = new MyPanel();
        add(panel);


        Button button = new Button("guzik");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hej, ktos mnie wcisnal");
                panel.drawRectAt(10, 10, new Dimension(scale, scale));
            }
        });

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
                    System.out.println("Does it work?");

                    Thread.sleep(1000);
                    play();

                    System.out.println("Nope, it doesnt...again.");
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
        apples = new ArrayList<>();
        score = 0;
    }

    public void updateAllSnake() {
        allSnake.clear();
        allSnake.add(head);
        allSnake.addAll(tail);
    }

    public void addTail() {

    }

    public void moveTail() {
        if (tail.size() == 0) {

        } else if (tail.size() == 1) {
            tail.get(0).setRect(getHeadLocation().getX(), getHeadLocation().getY(),
                    getHeadSize().width, getHeadSize().getHeight());
        } else {
            Rectangle2D tmp = tail.removeLast();
            tmp.setRect(getHeadLocation().getX(), getHeadLocation().getY(),
                    getHeadSize().width, getHeadSize().getHeight());
            tail.addFirst(tmp);
        }

    }

    void spawnApples() {
        Random rand = new Random();
        int valX = rand.nextInt(5);
        int valY = rand.nextInt(5);
        Ellipse2D apple = new Ellipse2D.Double(valX * scale, valY * scale, scale, scale);
        apples.add(apple);

    }

    void eatenApple() {
        score++;
        apples.clear();
        spawnApples();
    }

    public void play() {
        reset();
        panel.repaint();

        while (true) {
            eatenApple();

            moveSnake();
            updateAllSnake();
            panel.updateApples(apples);
            panel.updateSnake(allSnake);
            try {
                Thread.sleep(300);
                panel.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addSnake(int x, int y, Dimension size) {
        Rectangle2D rect = new Rectangle2D.Double(x, y, size.width, size.height);
        tail.add(rect);
    }

    public Direction getDirection() {
        return currentDirection;
    }

    public void moveSnake() {

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
