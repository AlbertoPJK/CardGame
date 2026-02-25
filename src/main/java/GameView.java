import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameView extends JFrame {

    private Game backend;
    public static final int WINDOW_WIDTH = 1000, WINDOW_HEIGHT = 750;


    public GameView(Game backend) {
        this.backend = backend;

        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setTitle("WAR!!!!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        g.setFont(new Font("Serif", Font.PLAIN, 30));

    }
}


