import javax.swing.*;

public class GameWindow extends JFrame {

    public GameWindow(){
        setTitle("A really crappy Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(320,345);
        setLocation(400,400);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {

        GameWindow gameWindow = new GameWindow();
    }
}
