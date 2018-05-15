import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener{
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int TOTAL_NUMBER_OF_DOTS = 400;
    private Image dot;
    private Image apple;
    private int apple_X;
    private int apple_Y;
    private int[] x = new int[TOTAL_NUMBER_OF_DOTS];
    private int[] y = new int[TOTAL_NUMBER_OF_DOTS];
    private int numberOfDots;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean gameIsRunning = true;
    private Timer timer;


    public GameField () {
        setBackground(Color.black);
        loadImages();
        initialize();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);

    }

    public void initialize () {
        numberOfDots = 3;
        for (int i = 0; i < numberOfDots; i++) {
            x[i] = 48 - i*DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(250,this);
        timer.start();
        createNewApple();
    }

    public void createNewApple () {
        apple_X = new Random().nextInt(20)*DOT_SIZE;
        apple_Y = new Random().nextInt(20)*DOT_SIZE;
    }

    public void loadImages () {
        ImageIcon iia = new ImageIcon("apple.png");
        ImageIcon iid = new ImageIcon("dot.png");
        apple = iia.getImage();
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent (Graphics g) {
        super.paintComponent(g);
        if (gameIsRunning) {
            g.drawImage(apple, apple_X, apple_Y, this);
            for (int i = 0; i < numberOfDots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        } else {
            String str = "Game Over";
            g.setColor(Color.white);
            g.drawString(str,125,SIZE/2);
        }
    }

    public void controls () {
        for (int i = numberOfDots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        } if (up) {
            y[0] -= DOT_SIZE;
        } if (down) {
            y[0] += DOT_SIZE;
        }
    }

    public void checkForCollisions () {
        for (int i = numberOfDots; i >0 ; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                gameIsRunning = false;
            }
        }

        if (x[0] > SIZE) {
            gameIsRunning = false;
        }
        if (x[0] < 0) {
            gameIsRunning = false;
        }
        if (y[0] > SIZE) {
            gameIsRunning = false;
        }
        if (y[0] < 0) {
            gameIsRunning = false;
        }
    }

    public void checkForApples () {
        if(x[0] == apple_X && y[0] == apple_Y){
            numberOfDots++;
            createNewApple();
        }
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (gameIsRunning) {
            checkForApples();
            checkForCollisions();
            controls();

        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_UP && !down) {
                right = false;
                up = true;
                left = false;
            }

            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_DOWN && !up) {
                right = false;
                down = true;
                left = false;
            }
        }
    }


}
