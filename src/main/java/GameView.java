import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameView extends JFrame {

    private Game backend;
    private Card playerCard;
    private Card computerCard;
    private String result;
    private int computerDeckSize;
    private int playerDeckSize;
    public static final int WINDOW_WIDTH = 1000, WINDOW_HEIGHT = 750;
    Image back = new ImageIcon("src/main/resources/Cards/back.png").getImage();


    public GameView(Game backend) {
        this.backend = backend;

        backend.getDeck();

        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setTitle("WAR!!!!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void results(Card computerCard, Card playerCard, String result, Integer computerDeckSize, Integer playerDeckSize) {
        this.computerCard = computerCard;
        this.playerCard = playerCard;
        this.result = result;
        this.computerDeckSize = computerDeckSize;
        this.playerDeckSize = playerDeckSize;
        repaint();
    }

    public void paint(Graphics g) {
        g.setFont(new Font("Serif", Font.PLAIN, 30));
        g.setColor(Color.white);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        if (playerCard == null) {
            g.setColor(Color.black);
            g.drawString("Game will commence. 'y' to play new round.", 150, 200);
            g.drawString("A is lowest, King is highest", 250, 300);
        }
        else {
            g.drawImage(computerCard.getImage(), 200, 275, 100, 150, this);
            g.drawImage(playerCard.getImage(), 600, 275, 100, 150, this);
            g.drawImage(back, 200, 475, 100, 150, this);
            g.drawImage(back, 600, 475, 100, 150, this);
            g.setColor(Color.black);
            g.drawString(result, 450, 200);
            g.drawString("Computer: " + computerDeckSize, 200, 100);
            g.drawString("You: " + playerDeckSize, 600, 100);
        }
    }
}


