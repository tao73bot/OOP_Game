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
    private final int BOARD_SIZE = 40;
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
        snake.add(new Point(10, 10));
        direction = 'R';
        gameOver = false;
        score = 0;
        delay = 200;

        spawnFood();

        timer = new Timer(delay, this);
        timer.start();
    }

    private void drawTile(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    private void drawSnake(Graphics g) {
        for (int i = 0; i < snake.size(); i++) {
            Point p = snake.get(i);
            Color color = (i == 0) ? Color.BLUE : Color.YELLOW; // Head is green, body is yellow
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

    private void move() {
        int dx = 0;
        int dy = 0;

        switch (direction) {
            case 'U':
                dy = -1;
                break;
            case 'D':
                dy = 1;
                break;
            case 'L':
                dx = -1;
                break;
            case 'R':
                dx = 1;
                break;
        }

        Point head = snake.get(0);
        Point newHead = new Point(head.x + dx, head.y + dy);

        if (newHead.equals(food)) {
            snake.add(0, newHead);
            spawnFood();
            score += 10;
            if (score % 100 == 0) {
                delay -= 10; // Increase difficulty every 50 points
                timer.setDelay(delay);
            }
        } else {
            snake.add(0, newHead);
            snake.remove(snake.size() - 1);
        }
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

    private boolean isGameOver() {
        Point head = snake.get(0);

        // Check if the snake has collided with itself or the wall
        if (head.x < 0 || head.x >= BOARD_SIZE || head.y < 0 || head.y >= BOARD_SIZE) {
            return true;
        }
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(i).equals(head)) {
                return true;
            }
        }

        return false;
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
        if (!gameOver) {
            move();
            gameOver = isGameOver();
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_W) && direction != 'D') {
            direction = 'U';
        } else if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) && direction != 'U') {
            direction = 'D';
        } else if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) && direction != 'R') {
            direction = 'L';
        } else if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) && direction != 'L') {
            direction = 'R';
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
