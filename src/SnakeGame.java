import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;

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

    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Survival of Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SnakeGame());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
