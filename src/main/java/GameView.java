import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {

    private Game backend;
    private Card playerCard;
    private Card computerCard;
    private String result;
    private int computerDeckSize;
    private int playerDeckSize;
    public static final int WINDOW_WIDTH = 1000, WINDOW_HEIGHT = 750;
    Image back = new ImageIcon("src/main/resources/Cards/back.png").getImage();

    private JPanel drawingPanel;

    public GameView(Game backend) {
        this.backend = backend;

        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setTitle("WAR!!!!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout()); // Organizes areas of the window

        // --- 1. Control Panel for Buttons ---
        JPanel controlPanel = new JPanel();
        JButton nextRoundButton = new JButton("Next Round");
        JButton restartButton = new JButton("Restart Game");

        // Tell the buttons what to do when clicked
        nextRoundButton.addActionListener(e -> backend.playNextRound());
        restartButton.addActionListener(e -> backend.restartGame());

        controlPanel.add(nextRoundButton);
        controlPanel.add(restartButton);
        this.add(controlPanel, BorderLayout.NORTH);

        // --- 2. Drawing Panel for Cards ---
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // Fixes the blank screen glitch!
                drawGame(g);
            }
        };
        drawingPanel.setBackground(Color.WHITE);
        this.add(drawingPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    public void resetView() {
        this.computerCard = null;
        this.playerCard = null;
        this.result = null;
        drawingPanel.repaint(); // Refreshes just the drawing area
    }

    public void results(Card computerCard, Card playerCard, String result, Integer computerDeckSize, Integer playerDeckSize) {
        this.computerCard = computerCard;
        this.playerCard = playerCard;
        this.result = result;
        this.computerDeckSize = computerDeckSize;
        this.playerDeckSize = playerDeckSize;
        drawingPanel.repaint();
    }

    // Your exact original graphics code, just moved here safely
    private void drawGame(Graphics g) {
        g.setFont(new Font("Serif", Font.PLAIN, 30));

        if (playerCard == null) {
            g.setColor(Color.black);
            g.drawString("Game is ready! Click 'Next Round' to start.", 150, 200);
            g.drawString("A is lowest, King is highest", 250, 300);
        }
        else {
            g.drawImage(computerCard.getImage(), 200, 200, 100, 150, this);
            g.drawImage(playerCard.getImage(), 600, 200, 100, 150, this);
            g.drawImage(back, 200, 400, 100, 150, this);
            g.drawImage(back, 600, 400, 100, 150, this);
            g.setColor(Color.black);
            g.drawString(result, 450, 125);
            g.drawString("Computer: " + computerDeckSize, 200, 50);
            g.drawString("You: " + playerDeckSize, 600, 50);
        }
    }
}