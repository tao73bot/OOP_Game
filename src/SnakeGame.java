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

    private void drawTile(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    private void drawSnake(Graphics g) {
        for (int i = 0; i < snake.size(); i++) {
            Point p = snake.get(i);
            Color color = (i == 0) ? Color.GREEN : Color.YELLOW; // Head is green, body is yellow
            drawTile(g, p.x, p.y, color);
            // Draw eyes on the snake's head
            if (i == 0) {
                g.setColor(Color.BLACK);
                switch (direction) {
                    case 'U':
                        g.fillOval(p.x * TILE_SIZE + 5, p.y * TILE_SIZE + 5, 5, 5);
                        g.fillOval(p.x * TILE_SIZE + 10, p.y * TILE_SIZE + 5, 5, 5);
                        break;
                    case 'D':
                        g.fillOval(p.x * TILE_SIZE + 5, p.y * TILE_SIZE + 10, 5, 5);
                        g.fillOval(p.x * TILE_SIZE + 10, p.y * TILE_SIZE + 10, 5, 5);
                        break;
                    case 'L':
                        g.fillOval(p.x * TILE_SIZE + 5, p.y * TILE_SIZE + 5, 5, 5);
                        g.fillOval(p.x * TILE_SIZE + 5, p.y * TILE_SIZE + 10, 5, 5);
                        break;
                    case 'R':
                        g.fillOval(p.x * TILE_SIZE + 10, p.y * TILE_SIZE + 5, 5, 5);
                        g.fillOval(p.x * TILE_SIZE + 10, p.y * TILE_SIZE + 10, 5, 5);
                        break;
                }
            }
        }
    }

    private void drawFood(Graphics g) {
        drawTile(g, food.x, food.y, Color.RED);
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Score: " + score, 10, 30);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawSnake(g);
        drawFood(g);
        drawScore(g);

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            String gameOverMessage = "Game Over!";
            int messageWidth = g.getFontMetrics().stringWidth(gameOverMessage);
            g.drawString(gameOverMessage, (TILE_SIZE * BOARD_SIZE - messageWidth) / 2, TILE_SIZE * BOARD_SIZE / 2);
        }
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
