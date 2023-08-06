import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements KeyListener, ActionListener {
    private final int TILE_SIZE = 20;
    private final int BOARD_SIZE = 20;
    private ArrayList<Point> snake;
    private Point food;
    private char direction;
    private Timer timer;
    private boolean gameOver;
    private int score;
    private int delay;

    public SnakeGame(){
        setPreferredSize(new Dimension(TILE_SIZE * BOARD_SIZE, TILE_SIZE * BOARD_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        snake = new ArrayList<>();
        snake.add(new Point(10, 10)); // Initial position of the snake
        direction = 'R'; // Start moving to the right
        gameOver = false;
        score = 0;
        delay = 200;

        spawnFood();

        timer = new Timer(delay, this);
        timer.start();
    }

    private void spawnFood() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(BOARD_SIZE);
            y = random.nextInt(BOARD_SIZE);
        } while (snake.contains(new Point(x, y)));

        food = new Point(x, y);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Survival of Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SnakeGame());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
